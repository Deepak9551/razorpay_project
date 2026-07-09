package com.codingshuttle.razorpay.payment.services.impl;

import com.codingshuttle.razorpay.common.exceptions.DuplicateResourse;
import com.codingshuttle.razorpay.payment.OrderRepository;
import com.codingshuttle.razorpay.payment.dto.CreateOrderRecord;
import com.codingshuttle.razorpay.payment.dto.CreateOrderResponse;
import com.codingshuttle.razorpay.payment.entity.OrderRecord;
import com.codingshuttle.razorpay.payment.services.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    @Value("${payment.order.default-expiration-minutes}")
    private int orderExpirationMinutes;
    
    @Override
    public CreateOrderResponse createOrder(UUID merchantId, CreateOrderRecord request) {
        if(request.receipt()!=null && orderRepository.existsByMerchantIdAndReceipt(merchantId,request.receipt())){
            throw new DuplicateResourse("ORDER_RECEIPT_DUPLICATE","order with the receipt already exists :"+request.receipt());
        }

        OrderRecord order = OrderRecord.builder()
                .merchantId(merchantId) // merchant id is known to identify - payment order
                .amount(request.amount())
                .receipt(request.receipt()) // order ID ( which is known to a merchant)
                .notes(request.notes()) // json meta data ( merchant can add any key value pair information)
                .expiredAt(request.expiredAt() != null ? request.expiredAt() : LocalDateTime.now().plusMinutes(orderExpirationMinutes)) // if expiredAt is not provided then set it to current time + orderExpirationMinutes
                .build();
       order =  orderRepository.save(order);

       // TODO: publish kafka event about order created ( so that other services can be notified about order created)

        return new CreateOrderResponse(order.getId(),
                merchantId,request.receipt(),
                request.amount()
                ,order.getStatus(),
                order.getAttempts()
                ,request.notes(),order.getExpiredAt()
                ,LocalDateTime.now()); // TODO: auditing will be added
    }
}
