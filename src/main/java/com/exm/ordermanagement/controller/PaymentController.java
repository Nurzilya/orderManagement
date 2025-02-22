package com.exm.ordermanagement.controller;

import com.exm.ordermanagement.dto.PaymentRequestDTO;
import com.exm.ordermanagement.dto.PaymentResponseDTO;
import com.exm.ordermanagement.service.MockPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Order API", description = "Endpoints for managing payments for orders")
public class PaymentController {
    private final MockPaymentService paymentService;

    public PaymentController(MockPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    @Operation(summary = "Manage payment process", description = "Manage payment process of order.")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        PaymentResponseDTO response = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(response);
    }
}
