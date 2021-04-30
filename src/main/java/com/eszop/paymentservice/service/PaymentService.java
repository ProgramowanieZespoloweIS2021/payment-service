package com.eszop.paymentservice.service;

import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public void save(Payment payment) {
        repository.save(payment);
    }

    public List<Payment> findAll() {
        return repository.findAll();
    }

    public Payment findById(Long id) {
        return repository.getOne(id);
    }
}
