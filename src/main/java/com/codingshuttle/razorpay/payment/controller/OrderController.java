package com.codingshuttle.razorpay.payment.controller;

import com.codingshuttle.razorpay.payment.dto.CreateOrderRecord;
import com.codingshuttle.razorpay.payment.dto.CreateOrderResponse;
import com.codingshuttle.razorpay.payment.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private UUID merchantId = UUID.fromString("fb2b2e2d-1875-4ccf-8600-0ea7864f2456"); // TODO: replace with merchant context

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRecord record) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(merchantId, record));
    }
}
