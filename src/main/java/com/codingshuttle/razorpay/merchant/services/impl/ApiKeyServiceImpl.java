package com.codingshuttle.razorpay.merchant.services.impl;

import com.codingshuttle.razorpay.common.exceptions.ResourceNotFoundException;
import com.codingshuttle.razorpay.common.utils.RandomizerUtils;
import com.codingshuttle.razorpay.merchant.dto.request.ApiKeyCreateRequest;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyResponse;
import com.codingshuttle.razorpay.merchant.entity.ApiKey;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.codingshuttle.razorpay.merchant.mapper.ApiKeyMapper;
import com.codingshuttle.razorpay.merchant.repositories.ApiKeyRepository;
import com.codingshuttle.razorpay.merchant.repositories.MerchantRepository;
import com.codingshuttle.razorpay.merchant.services.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyMapper apiKeyMapper;

    @Override
    public ApiKeyCreateResponse createApiKey(UUID merchantId, ApiKeyCreateRequest request) {
        // validate merchant ID
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("merchant", merchantId));
        String key = "rzp_" + request.environment().name().toUpperCase() + UUID.randomUUID().toString() + RandomizerUtils.randomBase64(24);
        String secret = RandomizerUtils.randomBase64(40); // TODO: generate secret using cryptography

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(key)
                .keySecretHash(secret)
                .environment(request.environment())
                .build();
        apiKey = apiKeyRepository.save(apiKey);
        // no use mapper because we need to send the raw secret to the merchant and store encrypted in db
        // apiKey( secret = (encrypted) )
        // apiKeyCreateResponse( secret = (raw) )
        // if we use mapper then we need to decrypt the secret or encrypted will be send to merchant
        return new ApiKeyCreateResponse(apiKey.getId(), key, secret, request.environment());
    }
    // list all merchant api keys
    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {

        // get all no need to check id ( in place empty list return )
        List<ApiKey> merchantApiKeys = apiKeyRepository.findByMerchant_Id(merchantId);
        return  apiKeyMapper.toResponseList(merchantApiKeys);
    }

    @Override
    @Transactional
    // entity is in persistent context - dirty check
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)  // findByIdAndMerchant_Id
                .filter(k -> k.getMerchant().getId().equals(merchantId)).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", "keyId"));
        log.info("revoke API KEY {} with KeyId {}",apiKey, apiKey.getKeyId());
        apiKey.setEnabled(false);
    }

    @Override
    @Transactional
    public ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findByIdAndMerchant_Id(keyId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", "keyId"));
        if(!apiKey.isEnabled()) throw new RuntimeException("ApiKey is not enabled");
        // rotate - secret ( currentSecret -> prevSecret -> newSecret ) // reduce downtime
        String newHashSecret = RandomizerUtils.randomBase64(40); // TODO: generate secret using cryptography

        apiKey.setPrevKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newHashSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodEndAt(LocalDateTime.now().plusHours(24));
        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(), newHashSecret, apiKey.getEnvironment());
    }
}
