package com.example.starbucksproject;

import java.util.Date;

public class GiftCard {
    private String cardNumber;
    private double balance;
    private Date expirationDate;
    private boolean isActive;

    public GiftCard(String cardNumber, double balance, Date expirationDate, boolean isActive) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }

    /** Setters and Getters **/

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void applyToOrder(double amount) { }

    public boolean isValid() {
        return false;
    }

}
