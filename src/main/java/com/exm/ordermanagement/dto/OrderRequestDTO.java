package com.exm.ordermanagement.dto;

import java.util.List;

public class OrderRequestDTO {
    private List<Long> productIds;
    private String email;
    private char seatLetter;
    private short seatNumber;

    // Constructors
    public OrderRequestDTO() {}

    public OrderRequestDTO(List<Long> productIds, String email, char seatLetter, short seatNumber) {
        this.productIds = productIds;
        this.email = email;
        this.seatLetter = seatLetter;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public char getSeatLetter() { return seatLetter; }
    public void setSeatLetter(char seatLetter) { this.seatLetter = seatLetter; }

    public short getSeatNumber() { return seatNumber; }
    public void setSeatNumber(short seatNumber) { this.seatNumber = seatNumber; }

}
