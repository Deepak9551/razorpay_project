package com.codingshuttle.razorpay.merchant.dto.request;

import com.codingshuttle.razorpay.common.enums.Environment;

public record ApiKeyCreateRequest(
        Environment environment
) {
}
