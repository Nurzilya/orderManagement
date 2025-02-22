package com.exm.ordermanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "card_token")
    private String cardToken;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_gateway")
    private String paymentGateway;

    public PaymentDetails() {
    }

    public PaymentDetails(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentDetails(String paymentGateway, LocalDateTime paymentDate, PaymentStatus paymentStatus, String cardToken, double totalPrice, Long id) {
        this.paymentGateway = paymentGateway;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.cardToken = cardToken;
        this.totalPrice = totalPrice;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", cardToken='" + cardToken + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", paymentDate=" + paymentDate +
                ", paymentGateway='" + paymentGateway + '\'' +
                '}';
    }
}
