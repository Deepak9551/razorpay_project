package com.codingshuttle.razorpay.payment.services;


import com.codingshuttle.razorpay.payment.dto.CreateOrderRecord;
import com.codingshuttle.razorpay.payment.dto.CreateOrderResponse;

import java.util.UUID;

public interface OrderService {
    CreateOrderResponse createOrder(UUID merchantId, CreateOrderRecord request);
}
