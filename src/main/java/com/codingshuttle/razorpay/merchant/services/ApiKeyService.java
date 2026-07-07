package com.codingshuttle.razorpay.merchant.services;

import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateRequest;
import com.codingshuttle.razorpay.merchant.dto.ApiKeyCreateResponse;

import java.util.UUID;

public interface ApiKeyService {

    public ApiKeyCreateResponse createApiKey(UUID merchantId, ApiKeyCreateRequest request);
}
