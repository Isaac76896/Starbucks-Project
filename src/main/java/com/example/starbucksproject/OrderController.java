package com.example.starbucksproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderController
{
    private Order currentOrder;
    private List<OrderItem> cart;
    private Customer customer;
    private RewardsAccount account;

    public OrderController() {

    }

    @FXML
    public void handleDubaiMatcha(ActionEvent event) { }

    @FXML
    public void handleLavenderMatcha(ActionEvent event) { }

    @FXML
    public void handleToastedCoconutMacchiato(ActionEvent event) { }

    @FXML
    public void handleToastedCoconutColdBrew(ActionEvent event) { }

    @FXML
    public void handlePlaceOrder(ActionEvent event) { }

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

    @FXML
    public void menuButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu Screen.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MenuController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void orderButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order Screen.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        OrderController controller = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void giftCardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gift Cards.fxml"));
        Parent root = loader.load();
        GiftCardController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void locationsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store Screen.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        StoreController controller = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void rewardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
        Parent root = loader.load();
        RewardsController controller = loader.getController();
        controller.setData(AppState.getInstance().getAccount(), AppState.getInstance().getCustomer());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAccount(RewardsAccount account) {
        this.account = account;
    }
}
