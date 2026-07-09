package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.enums.Environment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "api_keys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class  ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    // used by merchant to connect with razorpay
    // key id for merchant identification and secret hash for authentication
    @Column(length = 100,nullable = false , unique = true)
    private String keyId;
    @Column(length = 100,nullable = false)
    private String keySecretHash;
    @Column(length = 100)
    private String prevKeySecretHash;
    // many ApiKey belong to one merchant
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Environment environment;
    @Builder.Default
    private boolean enabled=true;

    // key used details
    private LocalDateTime lastUsedAt;
    private LocalDateTime rotatedAt;  // when key is rotated to create new key
    private LocalDateTime gracePeriodEndAt; // it is the time when key will be deleted
 //   private LocalDateTime deletedAt;
}
