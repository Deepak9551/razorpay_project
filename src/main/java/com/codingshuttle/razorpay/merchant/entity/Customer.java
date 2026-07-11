package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer",
indexes = {
        @Index(name = "idx_customer_merchant", columnList = "merchant_id")
        ,@Index(name = "idx_customer_email", columnList = "email")
}
)
public class Customer extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 100)
    private String name;
    @Column(length = 50)
    private String email;
    @Column(length = 15)
    private String contact;
    @Column(length = 255)
    private String address;
    private LocalDateTime deletedAt;

    // many customers belong to one merchant
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
}
