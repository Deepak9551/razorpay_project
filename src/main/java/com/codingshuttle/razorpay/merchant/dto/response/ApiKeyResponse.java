package com.codingshuttle.razorpay.merchant.dto.response;

import com.codingshuttle.razorpay.common.enums.Environment;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiKeyResponse (
        UUID id,
         String keyId,

Environment environment,
 boolean enabled,
 LocalDateTime lastUsedAt,
 LocalDateTime rotatedAt,
 LocalDateTime gracePeriodEndAt

){
}
