package com.codingshuttle.razorpay.merchant.dto;

import com.codingshuttle.razorpay.common.enums.Environment;

public record ApiKeyCreateRequest(
        Environment environment
) {
}
