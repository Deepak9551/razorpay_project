package com.codingshuttle.razorpay.payment.controller;

import com.codingshuttle.razorpay.payment.dto.request.CreateOrderRecord;
import com.codingshuttle.razorpay.payment.dto.response.OrderResponse;
import com.codingshuttle.razorpay.payment.dto.response.PaymentResponse;
import com.codingshuttle.razorpay.payment.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private UUID merchantId = UUID.fromString("fb2b2e2d-1875-4ccf-8600-0ea7864f2456"); // TODO: replace with merchant context

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRecord record) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(merchantId, record));

    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId) {

        return ResponseEntity.ok(orderService.getById(merchantId, orderId));

    }

    @GetMapping("/{orderId}/payments")
    public ResponseEntity<List<PaymentResponse>> listOfPayments(@PathVariable UUID orderId) {

        return ResponseEntity.ok(orderService.listOfPayments(merchantId, orderId));

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {

        orderService.cancel(merchantId, orderId);

        return ResponseEntity.noContent().build();

    }
}
