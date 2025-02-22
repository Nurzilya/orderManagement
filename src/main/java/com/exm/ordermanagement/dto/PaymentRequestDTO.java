package com.exm.ordermanagement.dto;

public class PaymentRequestDTO {
    private String cardToken;
    private double amount;
    private String paymentGateway;

    public PaymentRequestDTO() {}

    public PaymentRequestDTO(String cardToken, double amount, String paymentGateway) {
        this.cardToken = cardToken;
        this.amount = amount;
        this.paymentGateway = paymentGateway;
    }

    public String getCardToken() { return cardToken; }
    public void setCardToken(String cardToken) { this.cardToken = cardToken; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentGateway() { return paymentGateway; }
    public void setPaymentGateway(String paymentGateway) { this.paymentGateway = paymentGateway; }

}
