package com.codingshuttle.razorpay.merchant.dto.request;

import com.codingshuttle.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MerchantRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters long")
        String name,
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password should be at least 8 characters long")
        String password,
        String contact,
        String address,
        @NotBlank(message = "Bussiness name is required")
        @Size(min = 2, max = 100, message = "Bussiness name should be between 2 and 100 characters long")
        String bussinessName,

        BusinessType bussinessType

) {
}
