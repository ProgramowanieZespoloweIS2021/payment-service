package com.eszop.paymentservice.dto;

import com.eszop.paymentservice.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
    private Long id;

    private Long userId;

    private Double price;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private PaymentStatus status;
}
