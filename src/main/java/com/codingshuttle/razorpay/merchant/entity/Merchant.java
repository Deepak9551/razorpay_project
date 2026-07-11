package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import com.codingshuttle.razorpay.common.enums.BusinessType;
import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "merchant",
indexes = {
        @Index(name = "idx_merchant_status", columnList = "status")
}
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    // personal details
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "email", nullable = false, unique = true)
    String email;

    String contact;
    String address;
    // business details
    @Enumerated(EnumType.STRING)
    BusinessType businessType;
    String businessName;
    @Column(length = 200)
    String websiteUrl;

    // settlement bank details
    @Column(length = 100)
    String settlementBankName; // name of bank in IFCS code
    @Column(length = 100)
    String settlementBankAccountHolderName;
    @Column(length = 20)
    String settlementIfscCode;
//    String bankBranch; ( IFCS code) give bank branch

    // tax details
    @Column(length = 20)
    String panId;
    @Column(length = 20)
    String gstId;

    // status
    @Enumerated(EnumType.STRING)
    MerchantStatus status = MerchantStatus.PENDING_KYC;
}
