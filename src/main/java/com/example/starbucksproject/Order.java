package com.example.starbucksproject;

import java.util.Date;
import java.util.List;

public class Order {
    /**
     * Variables for Order class
     */
    private String orderId;
    private List<OrderItem> items;
    private String status;
    private double totalPrice;
    private Date timeStamp;

    /**
     * Order super class
     * @param orderId sets the order id
     * @param items sets the items to be ordered
     * @param status sets the status of the order
     * @param totalPrice sets the total price
     * @param timeStamp sets a time stamp of when the order was placed
     */

    public Order(String orderId, List<OrderItem> items, String status, double totalPrice, Date timeStamp) {
        this.orderId = orderId;
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
        this.timeStamp = timeStamp;
    }

    /* setters and getters */

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public double calcTotal() {
        double total = 0.0;

        for (OrderItem item : items) {
            total += item.calcSubtotal();
        }

        this.totalPrice = total;
        return total;
    }
}