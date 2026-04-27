package com.example.starbucksproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public abstract class BaseController {

    protected List<MenuItem> cartItems;
    protected double cartTotal;

    protected abstract VBox getCartPanel();

    protected void initCart() {
        cartItems = AppState.getInstance().getCartItems();
        cartTotal = AppState.getInstance().getCartTotal();
        if (getCartPanel() != null && !cartItems.isEmpty()) {
            getCartPanel().setVisible(true);
            getCartPanel().setManaged(true);
            updateCartPanel();
        }
    }

    protected void toggleCart() {
        if (getCartPanel() == null) return;
        boolean showing = getCartPanel().isVisible();
        getCartPanel().setVisible(!showing);
        getCartPanel().setManaged(!showing);
    }

    protected void addToCart(MenuItem item) {
        AppState.getInstance().addToCart(item);
        cartItems = AppState.getInstance().getCartItems();
        cartTotal = AppState.getInstance().getCartTotal();
        getCartPanel().setVisible(true);
        getCartPanel().setManaged(true);
        updateCartPanel();
    }

    protected void removeFromCart(MenuItem item) {
        AppState.getInstance().removeFromCart(item);
        cartItems = AppState.getInstance().getCartItems();
        cartTotal = AppState.getInstance().getCartTotal();
        updateCartPanel();
    }

    protected void updateCartPanel() {
        if (getCartPanel() == null) return;
        getCartPanel().getChildren().clear();

        Label title = new Label("Your Order");
        title.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
        getCartPanel().getChildren().add(title);

        if (cartItems.isEmpty()) {
            Label empty = new Label("No items added yet");
            empty.setStyle("-fx-font-size: 13; -fx-text-fill: gray;");
            getCartPanel().getChildren().add(empty);
        } else {
            for (MenuItem item : cartItems) {
                HBox row = new HBox(8);
                row.setAlignment(Pos.CENTER_LEFT);

                Label name = new Label(item.getName());
                name.setWrapText(true);
                name.setMaxWidth(140);
                name.setStyle("-fx-font-size: 12;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Label price = new Label(String.format("$%.2f", item.getPrice()));
                price.setStyle("-fx-font-size: 12;");

                Button removeBtn = new Button("✕");
                removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cc0000; " +
                        "-fx-cursor: hand; -fx-font-size: 11; -fx-padding: 0 4;");
                removeBtn.setOnAction(e -> removeFromCart(item));

                row.getChildren().addAll(name, spacer, price, removeBtn);
                getCartPanel().getChildren().add(row);
            }
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        getCartPanel().getChildren().add(spacer);

        Label total = new Label(String.format("Total: $%.2f", cartTotal));
        total.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        getCartPanel().getChildren().add(total);

        Button placeOrder = new Button("Place Order");
        placeOrder.setMaxWidth(Double.MAX_VALUE);
        placeOrder.setStyle("-fx-background-color: #1E3932; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-padding: 12; -fx-cursor: hand;");
        placeOrder.setOnAction(e -> placeOrder());
        getCartPanel().getChildren().add(placeOrder);
    }

    protected void placeOrder() {
        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Cart");
            alert.setHeaderText("Your cart is empty!");
            alert.setContentText("Please add items before placing an order.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Place Order");
        alert.setHeaderText("Confirm your order");
        alert.setContentText(String.format("Total: $%.2f\n\nPlace this order?", cartTotal));

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                int starsEarned = (int) (cartTotal * 2);

                AppState.getInstance().clearCart();
                cartItems = AppState.getInstance().getCartItems();
                cartTotal = AppState.getInstance().getCartTotal();
                updateCartPanel();

                RewardsAccount account = AppState.getInstance().getAccount();
                if (account != null) {
                    account.setTotalStars(account.getTotalStars() + starsEarned);
                    int stars = account.getTotalStars();
                    if (stars >= 2500) {
                        account.setTier("Reserve");
                    } else if (stars >= 500) {
                        account.setTier("Gold");
                    } else {
                        account.setTier("Green");
                    }
                }

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Order Placed");
                success.setHeaderText("Order placed successfully!");
                success.setContentText("Your order is being prepared!\nYou earned " + starsEarned + " stars!");
                success.showAndWait();
            }
        });
    }
}