package com.example.starbucksproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GiftCardController extends BaseController {
    private Customer customer;
    private RewardsAccount account;
    private double selectedAmount = 25.0;
    private String selectedDesign = "Classic Green";

    @FXML private Label amountLabel;
    @FXML private Label totalLabel;
    @FXML private Label giftCardAmountLabel;
    @FXML private TextField customAmountField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField messageField;
    @FXML private VBox cartPanel;

    public GiftCardController() {}

    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setAccount(RewardsAccount account) { this.account = account; }

    @Override
    protected VBox getCartPanel() { return cartPanel; }

    @FXML
    public void initialize() {
        updateOrderSummary();
        initCart();
    }

    private void updateOrderSummary() {
        String formatted = String.format("$%.0f", selectedAmount);
        if (amountLabel != null) amountLabel.setText(formatted);
        if (totalLabel != null) totalLabel.setText(formatted);
        if (giftCardAmountLabel != null) giftCardAmountLabel.setText(formatted);
    }

    @FXML public void selectClassicGreen(ActionEvent event) { selectedDesign = "Classic Green"; }
    @FXML public void selectBirthday(ActionEvent event) { selectedDesign = "Birthday"; }
    @FXML public void selectThankYou(ActionEvent event) { selectedDesign = "Thank you"; }

    @FXML public void select10(ActionEvent event) { selectedAmount = 10; updateOrderSummary(); }
    @FXML public void select25(ActionEvent event) { selectedAmount = 25; updateOrderSummary(); }
    @FXML public void select50(ActionEvent event) { selectedAmount = 50; updateOrderSummary(); }
    @FXML public void select100(ActionEvent event) { selectedAmount = 100; updateOrderSummary(); }

    @FXML
    public void purchaseGiftCard(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Successful");
        alert.setHeaderText("Gift Card Purchased!");
        alert.setContentText("A " + String.format("$%.0f", selectedAmount) + " gift card has been sent to " + emailField.getText());
        alert.showAndWait();
    }

    @FXML
    public void menuButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu Screen.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void locationsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store Screen.fxml"));
        Parent root = loader.load();
        StoreController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void rewardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
        Parent root = loader.load();
        RewardsController controller = loader.getController();
        controller.setData(account, customer);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.setScene(stage, root);
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
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void cartButtonClick(ActionEvent event) {
        toggleCart();
    }
}
