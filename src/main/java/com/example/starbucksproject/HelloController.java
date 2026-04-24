package com.example.starbucksproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HelloController {

    @FXML
    private Label cartStatusLabel;

    @FXML
    private Label totalLabel;

    private OrderController orderController;

    public void initialize() {
        orderController = new OrderController(null, new ArrayList<>());
        updateCartDisplay();
    }

    @FXML
    protected void handleDubaiMatcha() {
        MenuItem item = new MenuItem("M1", "Dubai Chocolate Matcha", 6.25, "Iced Drinks");
        OrderItem orderItem = new OrderItem(item, 1);
        orderController.addToCart(orderItem);
        updateCartDisplay();
    }

    @FXML
    protected void handleLavenderMatcha() {
        MenuItem item = new MenuItem("M2", "Lavender Matcha", 5.95, "Iced Drinks");
        OrderItem orderItem = new OrderItem(item, 1);
        orderController.addToCart(orderItem);
        updateCartDisplay();
    }

    @FXML
    protected void handleToastedCoconutMacchiato() {
        MenuItem item = new MenuItem("M3", "Toasted Coconut Macchiato", 6.45, "Hot Drinks");
        OrderItem orderItem = new OrderItem(item, 1);
        orderController.addToCart(orderItem);
        updateCartDisplay();
    }

    @FXML
    protected void handleToastedCoconutColdBrew() {
        MenuItem item = new MenuItem("M4", "Toasted Coconut Cold Brew", 5.75, "Cold Drinks");
        OrderItem orderItem = new OrderItem(item, 1);
        orderController.addToCart(orderItem);
        updateCartDisplay();
    }

    @FXML
    protected void handlePlaceOrder() {
        Order placedOrder = orderController.placeOrder();
        cartStatusLabel.setText("Order placed! Status: " + placedOrder.getStatus());
        totalLabel.setText(String.format("$%.2f", placedOrder.getTotalPrice()));
    }

    private void updateCartDisplay() {
        double total = 0.0;

        for (OrderItem item : orderController.getCart()) {
            total += item.calcSubtotal();
        }

        cartStatusLabel.setText("Items in cart: " + orderController.getCart().size());
        totalLabel.setText(String.format("$%.2f", total));
    }
}