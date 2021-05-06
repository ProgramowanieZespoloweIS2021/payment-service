package com.eszop.paymentservice.service;

import com.eszop.paymentservice.entity.Account;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.entity.PaymentStatus;
import com.eszop.paymentservice.repository.AccountRepository;
import com.eszop.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public AccountService(AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    public String pay(Long paymentId, Account account) {
        Account foundAccount = accountRepository.findByEmail(account.getEmail());
        if (foundAccount != null) {
            return changeBalance(foundAccount, paymentId);
        } else {
            account.setBalance(new BigDecimal(1000));
            return changeBalance(account, paymentId);
        }
    }

    private String changeBalance(Account account, Long paymentId) {
        BigDecimal balance = account.getBalance();
        Payment payment = paymentRepository.getOne(paymentId);
        if (balance.compareTo(payment.getPrice()) >= 0) {
            balance = balance.subtract(payment.getPrice());
            account.setBalance(balance);
            payment.setStatus(PaymentStatus.FINISHED);
            accountRepository.save(account);
            paymentRepository.save(payment);
            return "Balance was reduced by " + payment.getPrice();
        } else {
            return "Insufficient funds";
        }
    }
}
