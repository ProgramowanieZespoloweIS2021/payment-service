package com.eszop.paymentservice.unit;

import com.eszop.paymentservice.entity.Account;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.entity.PaymentStatus;
import com.eszop.paymentservice.repository.AccountRepository;
import com.eszop.paymentservice.repository.PaymentRepository;
import com.eszop.paymentservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeBalanceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PaymentRepository paymentRepository;

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        accountService = new AccountService(accountRepository, paymentRepository);
    }

    @Test
    public void negativePriceDetected() {
        Account account = new Account();
        account.setBalance(new BigDecimal("1000"));
        Payment payment = new Payment();
        payment.setPrice(new BigDecimal("-50"));

        String returnMessage = accountService.changeBalance(account, payment);

        assertEquals(returnMessage, "Insufficient funds");
    }

    @Test
    public void accountDoesNotHaveEnoughMoney() {
        Account account = new Account();
        account.setBalance(new BigDecimal("50"));
        Payment payment = new Payment();
        payment.setPrice(new BigDecimal("100"));

        String returnMessage = accountService.changeBalance(account, payment);
        assertEquals(returnMessage, "Insufficient funds");
    }

    @Test
    public void accountHasEnoughMoney() {
        Account account = new Account(1L,"mail",new BigDecimal("1000"),"x","y","123123123123","21/07","111");
        Payment payment = new Payment(1L,1L,new BigDecimal("100"), LocalDateTime.now(), LocalDateTime.now(),PaymentStatus.IN_PROGRESS, List.of("title"));
        payment.setPrice(new BigDecimal("100"));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);

        String returnMessage = accountService.changeBalance(account, payment);

        assertEquals(returnMessage, "Balance was reduced by " + payment.getPrice());
        assertEquals(payment.getStatus(), PaymentStatus.FINISHED);
    }

}
