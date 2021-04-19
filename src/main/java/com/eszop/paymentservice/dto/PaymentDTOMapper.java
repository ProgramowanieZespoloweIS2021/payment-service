package com.eszop.paymentservice.dto;

import com.eszop.paymentservice.entity.Payment;

public class PaymentDTOMapper {
    public static PaymentDTO map(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setUserId(payment.getUserId());
        paymentDTO.setPrice(payment.getPrice());
        paymentDTO.setUpdateTime(payment.getUpdateTime());
        paymentDTO.setCreateTime(payment.getCreateTime());
        paymentDTO.setStatus(payment.getStatus());
        return paymentDTO;
    }

    public static Payment map(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setUserId(paymentDTO.getUserId());
        payment.setPrice(paymentDTO.getPrice());
        payment.setUpdateTime(paymentDTO.getUpdateTime());
        payment.setCreateTime(paymentDTO.getCreateTime());
        payment.setStatus(paymentDTO.getStatus());
        return payment;
    }
}
