package com.codingshuttle.razorpay.merchant.services.impl;

import com.codingshuttle.razorpay.common.exceptions.ResourceNotFoundException;
import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateRequest;
import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.entity.ApiKey;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.codingshuttle.razorpay.merchant.repositories.ApiKeyRepository;
import com.codingshuttle.razorpay.merchant.repositories.MerchantRepository;
import com.codingshuttle.razorpay.merchant.services.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ApiKeyServiceImpl implements ApiKeyService {

private final MerchantRepository merchantRepository;
private final ApiKeyRepository apiKeyRepository;
    @Override
    public ApiKeyCreateResponse createApiKey(UUID merchantId, ApiKeyCreateRequest request) {
      // validate merchant ID
      Merchant merchant = merchantRepository.findById(merchantId)
               .orElseThrow(()-> new ResourceNotFoundException("merchant",merchantId));
       String key = "rzp_"+request.environment().name().toUpperCase()+UUID.randomUUID().toString();
       String secret = "hash_secret"; // TODO: generate secret using cryptography

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(key)
                .secretHash(secret)
                .environment(request.environment())
                .build();
        apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(),key,secret,request.environment());
    }
}
