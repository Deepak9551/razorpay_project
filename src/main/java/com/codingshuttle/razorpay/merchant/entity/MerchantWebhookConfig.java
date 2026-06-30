package com.codingshuttle.razorpay.merchant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "merchant_webhook_config")
public class MerchantWebhookConfig {
    // this details are used to configure webhook
    // this details are used by the payment gateway to the payment details on event
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // why merchat many webhook to one merchant
    // merchant can have multiple webhook because different events can be send to single merchant ( order.created, order.updated, order.deleted)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    private String eventType;

    private String webHashSecret;

    private String targetUrl;

    private boolean enabled;
}
