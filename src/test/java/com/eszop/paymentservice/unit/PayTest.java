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

public class PayTest {
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
    public void paymentStatusNotInProgress() {
        Account account = new Account();
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.FINISHED);
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(paymentRepository.getOne(Mockito.anyLong())).thenReturn(payment);

        String returnMessage = accountService.pay(1L, account);

        assertEquals(returnMessage, "Payment has been finished or canceled");
    }

    @Test
    public void noExistingAccountFound() {
        Account account = new Account(1L,"mail",new BigDecimal("1000"),"x","y","123123123123","21/07","111");
        Payment payment = new Payment(1L,1L,new BigDecimal("100"), LocalDateTime.now(), LocalDateTime.now(),PaymentStatus.IN_PROGRESS, List.of("title"));
        payment.setStatus(PaymentStatus.IN_PROGRESS);
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(paymentRepository.getOne(Mockito.anyLong())).thenReturn(payment);

        String returnMessage = accountService.pay(1L, account);

        assertEquals(returnMessage, "Balance was reduced by " + payment.getPrice());
    }

    @Test
    public void accountEqualsExistingAccount() {
        Account account = new Account(1L,"mail",new BigDecimal("1000"),"x","y","123123123123","21/07","111");
        Payment payment = new Payment(1L,1L,new BigDecimal("100"), LocalDateTime.now(), LocalDateTime.now(),PaymentStatus.IN_PROGRESS, List.of("title"));
        payment.setStatus(PaymentStatus.IN_PROGRESS);
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(account);
        Mockito.when(paymentRepository.getOne(Mockito.anyLong())).thenReturn(payment);

        String returnMessage = accountService.pay(1L, account);

        assertEquals(returnMessage, "Balance was reduced by " + payment.getPrice());
    }

    @Test
    public void accountDataIsNotValid() {
        Account account = new Account(1L,"mail",new BigDecimal("1000"),"x","y","123123123123","21/07","111");
        Payment payment = new Payment(1L,1L,new BigDecimal("100"), LocalDateTime.now(), LocalDateTime.now(),PaymentStatus.IN_PROGRESS, List.of("title"));
        payment.setStatus(PaymentStatus.IN_PROGRESS);
        Mockito.when(accountRepository.findByEmail(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(paymentRepository.getOne(Mockito.anyLong())).thenReturn(payment);

        String returnMessage = accountService.pay(1L, account);

        assertEquals(returnMessage, "account data is invalid");
    }

}
