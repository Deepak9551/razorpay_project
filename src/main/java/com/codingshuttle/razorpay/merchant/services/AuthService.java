package com.codingshuttle.razorpay.merchant.services;

import com.codingshuttle.razorpay.merchant.dto.request.MerchantRequest;
import com.codingshuttle.razorpay.merchant.dto.response.MerchantResponse;

public interface AuthService {
    public MerchantResponse signUp(MerchantRequest merchantRequest);
}
