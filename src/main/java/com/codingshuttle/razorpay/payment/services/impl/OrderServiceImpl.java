package com.codingshuttle.razorpay.payment.services.impl;

import com.codingshuttle.razorpay.common.enums.OrderStatus;
import com.codingshuttle.razorpay.common.exceptions.BusinessRuleViolationException;
import com.codingshuttle.razorpay.common.exceptions.DuplicateResourse;
import com.codingshuttle.razorpay.common.exceptions.ResourceNotFoundException;
import com.codingshuttle.razorpay.payment.mapper.OrderMappper;
import com.codingshuttle.razorpay.payment.repositories.PaymentRepository;
import com.codingshuttle.razorpay.payment.entity.Payment;
import com.codingshuttle.razorpay.payment.mapper.PaymentMapper;
import com.codingshuttle.razorpay.payment.repositories.OrderRepository;
import com.codingshuttle.razorpay.payment.dto.request.CreateOrderRecord;
import com.codingshuttle.razorpay.payment.dto.response.OrderResponse;
import com.codingshuttle.razorpay.payment.dto.response.PaymentResponse;
import com.codingshuttle.razorpay.payment.entity.OrderRecord;
import com.codingshuttle.razorpay.payment.services.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    @Value("${payment.order.default-expiration-minutes}")
    private int orderExpirationMinutes;

    private final PaymentMapper paymentMapper;

    private final PaymentRepository paymentRepository;

    private final OrderMappper orderMappper;
    
    @Override
    public OrderResponse createOrder(UUID merchantId, CreateOrderRecord request) {
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

        return  orderMappper.toResponse(order); // TODO: auditing will be added
    }
    // search order record for a given merchant id and order id
    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        // orderRepo -> findByIdAndMerchantId
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                // resourceName = Order
                // resourceId = orderId
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId"));


        return orderMappper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId"));
        if(order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.CANCELLED ){
            throw new BusinessRuleViolationException("ORDER_CANNOT_CANCELLED", "order cannot be cancelled with orderId : "+orderId);
        }
        // valid order - update payment.order status -> CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        return orderMappper.toResponse(order);
    }

    @Override
    @Transactional
    public List<PaymentResponse> listOfPayments(UUID merchantId, UUID orderId) {
        // multiple payments can be associated with a single order ( case of fail attempts)
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(merchantId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId"));

        List<Payment> payments = paymentRepository.findByOrderRecord_Id(orderRecord.getId());
        return paymentMapper.toResponseList(payments);
    }
}
