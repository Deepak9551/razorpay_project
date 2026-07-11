package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "merchant_webhook_config",
indexes = {
        @Index(name = "idx_merchant_webhook_merchant_enabled", columnList = "merchant_id,enabled"),

}
)
public class MerchantWebhookConfig extends BaseEntity {
    // this details are used to configure webhook
    // this details are used by the payment gateway to the payment details on event
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
