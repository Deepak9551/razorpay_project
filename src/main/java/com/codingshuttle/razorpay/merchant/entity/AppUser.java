package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // many AppUser blong to one Merchant
    @ManyToOne

    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

}
