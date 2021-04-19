package com.eszop.paymentservice.dto;

import com.eszop.paymentservice.entity.PaymentStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
//@Builder(builderClassName = "Builder", toBuilder = true)
//@JsonDeserialize(builder = PaymentDTO.Builder.class)
public class PaymentDTO {
    private Long id;

    private Long userId;

    private Double price;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private PaymentStatus status;

    public PaymentDTO() {
    }

//    @JsonPOJOBuilder(withPrefix = "")
//    public static class Builder {
//
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
