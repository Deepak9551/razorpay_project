package com.codingshuttle.razorpay.vault.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// this store the card information in encrypted form
@Entity
@Getter
@Setter
public class VaultCard extends BaseEntity {
// rule - store the sensitive data in encrypted form
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "encrypted_pan", nullable = false)
    private byte[] encryptedPan; // the encrypt algorithm return a byte of array
    @Column(name = "encrypted_dek", nullable = false)
    private byte[] encryptedDek; // the random string used to encrypt the pan ( decrypt by master key)
    @Column(name = "last_four", nullable = false,length = 4)
    private String lastFour;

    @Column(name = "bin", nullable = false)
    private String bin; // first 6 digits

    @Column(name = "brand", nullable = false)
    private String brand; // VISA, MasterCard, etc
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;
@Column(nullable = false)
    private String expiryMonth;
@Column(nullable = false)
    private String year;

    private LocalDateTime deletedAt;
}
