package com.codingshuttle.razorpay.merchant.controller;

import com.codingshuttle.razorpay.merchant.dto.request.ApiKeyCreateRequest;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyResponse;
import com.codingshuttle.razorpay.merchant.services.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{merchantId}/api-keys")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyCreateResponse> createApiKey(@PathVariable UUID merchantId, @Valid @RequestBody ApiKeyCreateRequest request) {
        return ResponseEntity.ok(apiKeyService.createApiKey(merchantId, request));
    }
@GetMapping
    public ResponseEntity<List<ApiKeyResponse>> listOfMerchantApiKey(@PathVariable UUID merchantId){
        return ResponseEntity.ok(apiKeyService.listByMerchant(merchantId));
    }

    @DeleteMapping("{apikeyId}/revoke")
    public ResponseEntity<Void> revokeApiKey(@PathVariable UUID merchantId ,@PathVariable UUID apikeyId){
       apiKeyService.revoke(merchantId,apikeyId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("{apikeyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotateApiKey(@PathVariable UUID merchantId ,@PathVariable UUID apikeyId){
        return ResponseEntity.ok(apiKeyService.rotate(merchantId,apikeyId));

    }
}
