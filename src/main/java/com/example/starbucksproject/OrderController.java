package com.example.starbucksproject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController
{
    private Order currentOrder;
    private List<OrderItem> cart;

    public OrderController(Order currentOrder, List<OrderItem> cart)
    {
        this.currentOrder = currentOrder;
        this.cart = (cart != null) ? cart : new ArrayList<>();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void setCart(List<OrderItem> cart) {
        this.cart = cart;
    }

    public List<OrderItem> getCart() {
        return cart;
    }

    public void addToCart(OrderItem item) {
        cart.add(item);
    }

    public void removeFromCart(OrderItem item) {
        cart.remove(item);
    }

    public void customization(OrderItem item, Customization customization) {
        item.addCustomization(customization);
    }

    public Order placeOrder() {
        String orderId = UUID.randomUUID().toString();

        currentOrder = new Order(
                orderId,
                new ArrayList<>(cart),
                "Placed",
                0.0,
                new Date()
        );

        currentOrder.calcTotal();
        cart.clear();
        return currentOrder;
    }

    public String trackOrder()
    {
        if (currentOrder == null)
        {
            return "No order has been placed.";
        }
        return currentOrder.getStatus();
    }
}