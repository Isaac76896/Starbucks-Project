package com.example.starbucksproject;

public class AppState {
    private static AppState instance;
    private Customer customer;
    private RewardsAccount account;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) instance = new AppState();
        return instance;
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public RewardsAccount getAccount() { return account; }
    public void setAccount(RewardsAccount account) { this.account = account; }
}