package com.example.starbucksproject;

import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private Customer customer;
    private RewardsAccount account;
    private List<MenuItem> cartItems =  new ArrayList<>();
    private double cartTotal = 0.0;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) instance = new AppState();
        return instance;
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public RewardsAccount getAccount() { return account; }
    public void setAccount(RewardsAccount account) { this.account = account; }
    public List<MenuItem> getCartItems() { return cartItems; }
    public void setCartItems(List<MenuItem> cartItems) { this.cartItems = cartItems; }
    public double getCartTotal() { return cartTotal; }
    public void setCartTotal(double cartTotal) { this.cartTotal = cartTotal; }

    public void addToCart(MenuItem item) {
        cartItems.add(item);
        if (item.getPrice() > 0) cartTotal += item.getPrice();
    }

    public void removeFromCart(MenuItem item) {
        cartItems.remove(item);
        if (item.getPrice() > 0) cartTotal -= item.getPrice();
    }

    public void clearCart() {
        cartItems.clear();
        cartTotal = 0.0;
    }
}