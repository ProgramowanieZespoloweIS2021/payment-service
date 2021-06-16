package com.eszop.paymentservice.unit;

import com.eszop.paymentservice.entity.Account;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.entity.PaymentStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentTest {
    @ParameterizedTest
    @CsvSource({
            "1, 1, 100, FINISHED",
            "2, 1, 200, IN_PROGRESS"
    })
    public void Can_create_order(Long id, Long userId, BigDecimal price, PaymentStatus status) {

        Payment payment = new Payment(id, userId, price, LocalDateTime.now(), LocalDateTime.now(), status, List.of("test"));

        assertThat(payment.getId(), is(equalTo(id)));
        assertThat(payment.getUserId(), is(equalTo(userId)));
        assertThat(payment.getPrice(), is(equalTo(price)));
        assertThat(payment.getStatus(), is(equalTo(status)));
    }

}
