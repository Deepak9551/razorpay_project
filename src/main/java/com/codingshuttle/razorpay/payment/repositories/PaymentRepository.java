package com.codingshuttle.razorpay.payment.repositories;

import com.codingshuttle.razorpay.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID>{


    List<Payment> findByOrderRecord_Id(UUID id);
}
