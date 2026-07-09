package com.codingshuttle.razorpay.merchant.repositories;

import com.codingshuttle.razorpay.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    List<ApiKey> findByMerchant_Id(UUID id);

    Optional<ApiKey> findByIdAndMerchant_Id(UUID id, UUID id1);
}
