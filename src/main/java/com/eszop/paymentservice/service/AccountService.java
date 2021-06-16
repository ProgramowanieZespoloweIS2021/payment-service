package com.eszop.paymentservice.service;

import com.eszop.paymentservice.entity.Account;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.entity.PaymentStatus;
import com.eszop.paymentservice.repository.AccountRepository;
import com.eszop.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.eszop.paymentservice.entity.PaymentStatus.IN_PROGRESS;

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
        Payment payment = paymentRepository.getOne(paymentId);
        if (payment.getStatus() != IN_PROGRESS){
            return "Payment has been finished or canceled";
        }
        if (foundAccount != null) {
            if (foundAccount.equals(account)){
                return changeBalance(foundAccount, payment);
            }
            else{
                return "account data is invalid";
            }
        } else {
            account.setBalance(new BigDecimal(1000));
            return changeBalance(account, payment);
        }
    }

    public String changeBalance(Account account, Payment payment) {
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(payment.getPrice()) >= 0 && payment.getPrice().compareTo(new BigDecimal("0")) > 0) {
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
