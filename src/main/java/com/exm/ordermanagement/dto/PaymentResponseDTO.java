package com.exm.ordermanagement.dto;

import com.exm.ordermanagement.entity.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String transactionId;

    public PaymentResponseDTO() {}

    public PaymentResponseDTO(PaymentStatus status, LocalDateTime paymentDate, String transactionId) {
        this.status = status;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

}
