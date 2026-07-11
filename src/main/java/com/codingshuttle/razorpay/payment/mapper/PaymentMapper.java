package com.codingshuttle.razorpay.payment.mapper;

import com.codingshuttle.razorpay.payment.dto.response.PaymentResponse;
import com.codingshuttle.razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "orderRecord.id", target = "orderId")

    PaymentResponse toResponse(Payment payment);

    // collection mapping auto call toResponse for each payment object
    List<PaymentResponse> toResponseList(List<Payment> payments);
}
