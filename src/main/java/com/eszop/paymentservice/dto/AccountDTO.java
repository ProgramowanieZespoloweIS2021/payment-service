package com.eszop.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class AccountDTO {
    private Long id;
    private BigDecimal balance;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "Not valid email format")
    private String email;

    @NotEmpty(message = "name should not be empty")
    private String name;

    @NotEmpty(message = "surname should not be empty")
    private String surname;

    @NotEmpty(message = "cartNumber should not be empty")
    @Size(min = 16, max = 16, message = "cartNumber should be 16 numbers long")
    private String cartNumber;

    @NotEmpty(message = "expirationDate should not be empty")
    @Size(min = 5, max = 5, message = "expirationDate should consist of year and month double digits code separated by '/'")
    private String expirationDate;

    @NotEmpty(message = "codeCvv should not be empty")
    @Size(min = 3, max = 3, message = "codeCvv should be 3 numbers long")
    private String codeCvv;
}