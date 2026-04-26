package com.example.starbucksproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class User {
    private String userId;
    private String name;
    private String email;
    private String passwordHash;

    public User(String userId, String name, String email, String passwordHash) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Setters and Getters //

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean authenticate() {
        return false;
    }

    public void sendGiftCardNotification(String recipientEmail, double amount) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Gift Card Sent");
        alert.setHeaderText("Email Sent Successfully!");
        alert.setContentText(
                "A gift card of $" + amount +
                        " has been purchased and sent to:\n" + recipientEmail
        );

        alert.showAndWait();
    }
}