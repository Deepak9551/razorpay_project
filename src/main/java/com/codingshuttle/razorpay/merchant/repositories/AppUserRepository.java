package com.codingshuttle.razorpay.merchant.repositories;

import com.codingshuttle.razorpay.merchant.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// to store the details of Merchant App Users
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
}
