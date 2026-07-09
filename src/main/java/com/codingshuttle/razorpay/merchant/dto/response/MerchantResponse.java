package com.codingshuttle.razorpay.merchant.dto.response;

import com.codingshuttle.razorpay.common.enums.BusinessType;

import java.util.UUID;

public record MerchantResponse(

        UUID id,
        String name,
        String email,
        String contact,
        String address,
        BusinessType businessType,
        String businessName

        

        

) {
}
