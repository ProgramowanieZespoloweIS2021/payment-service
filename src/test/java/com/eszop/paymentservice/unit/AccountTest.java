package com.eszop.paymentservice.unit;

import com.eszop.paymentservice.entity.Account;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

public class AccountTest {
    @ParameterizedTest
    @CsvSource({
            "1, test@test.com, 100, X, Z, 123321321, 21/12, 111",
            "2, test1@test.com, 200, Y, Z, 123455611, 01/02, 222"
    })
    public void Can_create_order(Long id, String email, BigDecimal balance, String name, String surname, String cardNumber, String expirationDate, String codeCvv) {

        Account account = new Account(id, email, balance, name, surname, cardNumber, expirationDate, codeCvv);

        assertThat(account.getId(), is(equalTo(id)));
        assertThat(account.getEmail(), is(equalTo(email)));
        assertThat(account.getName(), is(equalTo(name)));
        assertThat(account.getSurname(), is(equalTo(surname)));
        assertThat(account.getCartNumber(), is(equalTo(cardNumber)));
        assertThat(account.getExpirationDate(), is(equalTo(expirationDate)));
        assertThat(account.getCodeCvv(), is(equalTo(codeCvv)));
    }

}
