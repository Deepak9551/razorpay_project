package com.codingshuttle.razorpay.merchant.controller;

import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateRequest;
import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.services.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
