package com.exm.ordermanagement.entity;

import jakarta.persistence.*;

@Embeddable
public class BuyerDetails {

    private String email;

    private char seatLetter;

    private short seatNumber;

    public BuyerDetails() {

    }

    public BuyerDetails(String email, char seatLetter, short seatNumber) {
        this.seatNumber = seatNumber;
        this.seatLetter = seatLetter;
        this.email = email;
    }

    public BuyerDetails(char seatLetter, short seatNumber) {
        this.seatNumber = seatNumber;
        this.seatLetter = seatLetter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(char seatLetter) {
        this.seatLetter = seatLetter;
    }

    public short getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(short seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "BuyerDetail{" +
                ", buyerEmail='" + email + '\'' +
                ", seatLetter='" + seatLetter + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
