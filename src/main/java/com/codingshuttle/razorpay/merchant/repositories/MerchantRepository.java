package com.codingshuttle.razorpay.merchant.repositories;

import com.codingshuttle.razorpay.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
// to store the details of the merchant
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    boolean existsByEmail(String email);
}
