package com.eszop.paymentservice.controller;

import com.eszop.paymentservice.dto.PaymentDTO;
import com.eszop.paymentservice.dto.PaymentDTOMapper;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {

//    @Autowired
//    PaymentRepository repository;
    private final PaymentRepository repository;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(@RequestBody PaymentDTO paymentDTO) {
        repository.save(PaymentDTOMapper.map(paymentDTO));
    }

    // TODO: 19.04.2021 for tests only
    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return repository.findAll().stream().map(PaymentDTOMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PaymentDTO getPayment(@PathVariable Long id) {
        Payment payment = repository.getOne(id);
        return PaymentDTOMapper.map(payment);
    }

    @PostMapping("/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = repository.getOne(id);
        payment.setStatus(paymentDTO.getStatus());
        repository.save(payment);
    }
}
