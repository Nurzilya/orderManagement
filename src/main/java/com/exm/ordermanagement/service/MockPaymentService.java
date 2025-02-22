package com.exm.ordermanagement.service;

import com.exm.ordermanagement.dto.PaymentRequestDTO;
import com.exm.ordermanagement.dto.PaymentResponseDTO;
import com.exm.ordermanagement.entity.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class MockPaymentService {

    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        // Simulating different payment scenarios
        PaymentStatus status = determinePaymentStatus();
        String transactionId = UUID.randomUUID().toString();

        return new PaymentResponseDTO(status, LocalDateTime.now(), transactionId);
    }

    private PaymentStatus determinePaymentStatus() {
        Random random = new Random();
        int result = random.nextInt(100); // Generate a random number between 0-99

        if (result < 80) {
            return PaymentStatus.PAID; // 80% chance of successful payment
        } else if (result < 90) {
            return PaymentStatus.PAYMENT_FAILED; // 10% chance of payment failure
        } else {
            return PaymentStatus.OFFLINE_PAYMENT; // 10% chance of offline payment
        }
    }
}
