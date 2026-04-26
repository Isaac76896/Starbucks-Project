package com.example.starbucksproject;

import java.util.Date;
import java.util.UUID;

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

    // Optional helper constructor (auto-generate card number)
    public GiftCard(double balance, Date expirationDate) {
        this.cardNumber = UUID.randomUUID().toString();
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.isActive = true;
    }

    // Getters and Setters //

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

    // Apply card to an order *//
    public void applyToOrder(double amount) {
        if (isValid() && amount > 0) {
            if (balance >= amount) {
                balance -= amount;
            } else {
                balance = 0;
            }
        }
    }

    // Check if card is usable *//
    public boolean isValid() {
        Date now = new Date();
        return isActive && expirationDate.after(now) && balance > 0;
    }

    @Override
    public String toString() {
        return "Card#: " + cardNumber +
                " | Balance: $" + balance +
                " | Expires: " + expirationDate +
                " | Active: " + isActive;
    }
}