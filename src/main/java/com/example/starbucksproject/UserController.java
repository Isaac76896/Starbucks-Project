package com.example.starbucksproject;

import java.util.List;

public class UserController {
    private User currentUser;
    private String authToken;

    public UserController(User currentUser, String authToken) {
        this.currentUser = currentUser;
        this.authToken = authToken;
    }

    /** Setters and Getters**/

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean login() {
        return false;
    }

    public void logout() {}

    public void updateProfile() {}

    public List<Order> viewOrderHistory() {
        return null;
    }
}
