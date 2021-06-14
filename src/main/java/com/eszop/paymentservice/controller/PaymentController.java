package com.eszop.paymentservice.controller;

import com.eszop.paymentservice.dto.AccountDTO;
import com.eszop.paymentservice.dto.PaymentDTO;
import com.eszop.paymentservice.entity.Account;
import com.eszop.paymentservice.entity.Payment;
import com.eszop.paymentservice.service.AccountService;
import com.eszop.paymentservice.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {
    private final ModelMapper modelMapper = new ModelMapper();
    private final PaymentService paymentService;
    private final AccountService accountService;

    public PaymentController(PaymentService paymentService, AccountService accountService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.save(modelMapper.map(paymentDTO, Payment.class));
        PaymentDTO newPaymentDTO = modelMapper.map(payment, PaymentDTO.class);
        return addLinkToPaymentDTO(newPaymentDTO, newPaymentDTO.getId());
    }

    @GetMapping("/{id}")
    public PaymentDTO getPayment(@PathVariable Long id) {
        PaymentDTO newPaymentDTO = modelMapper.map(paymentService.findById(id), PaymentDTO.class);
        return addLinkToPaymentDTO(newPaymentDTO, id);
    }

    @GetMapping
    public List<PaymentDTO> getPayments(@RequestParam(name = "user_id") Long userId) {
        return paymentService.findByUserId(userId).stream()
                .map(p -> modelMapper.map(p, PaymentDTO.class))
                .map(p -> addLinkToPaymentDTO(p, p.getId()))
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        Payment payment = paymentService.findById(id);
        payment.setStatus(paymentDTO.getStatus());
        paymentService.save(payment);
    }

    @PostMapping("/{paymentId}/pay")
    public Map<String, String> pay(@PathVariable Long paymentId, @Valid @RequestBody AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        return Map.of("message", accountService.pay(paymentId, account));
    }

    private PaymentDTO addLinkToPaymentDTO(PaymentDTO paymentDTO, Long id) {
        Link linkToSelf = linkTo(methodOn(PaymentController.class).getPayment(id)).withSelfRel();
        Link linkToPay = linkTo(methodOn(PaymentController.class).pay(id, new AccountDTO())).withRel("pay");
        return paymentDTO.add(linkToSelf, linkToPay);
    }
}
