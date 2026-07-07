package com.codingshuttle.razorpay.merchant.services;

import com.codingshuttle.razorpay.merchant.dto.MerchantRequest;
import com.codingshuttle.razorpay.merchant.dto.MerchantResponse;

public interface AuthService {
    public MerchantResponse signUp(MerchantRequest merchantRequest);
}
