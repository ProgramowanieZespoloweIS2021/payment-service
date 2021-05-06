package com.eszop.paymentservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private BigDecimal balance;

    private String name;

    private String surname;

    private String cartNumber;

    private String expirationDate;

    private String codeCvv;
}
