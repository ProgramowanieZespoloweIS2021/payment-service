package com.eszop.paymentservice.dto;

import com.eszop.paymentservice.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
    private Long id;

    private Long userId;

    private BigDecimal price;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private PaymentStatus status;
}
