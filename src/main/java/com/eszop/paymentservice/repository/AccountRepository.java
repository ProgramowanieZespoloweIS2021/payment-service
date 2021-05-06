package com.eszop.paymentservice.repository;

import com.eszop.paymentservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}