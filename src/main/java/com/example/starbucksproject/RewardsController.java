package com.example.starbucksproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RewardsController extends BaseController {
    private RewardsAccount account;
    private Customer customer;

    /** FXML fields go here **/
    @FXML Button freeBirthdayDrinkButton;
    @FXML Button exclusiveOffersButton;
    @FXML Button fastOrderingButton;
    @FXML Button menuButton;
    @FXML Button locationsButton;
    @FXML Button rewardsButton;
    @FXML Button giftCardsButton;
    @FXML Button cartButton;
    @FXML TextArea howitWorksArea;
    @FXML Label starBalanceLabel;
    @FXML ProgressBar tierProgressBar;
    @FXML Label tierGoalLabel;
    @FXML Label welcomeLabel;
    @FXML private VBox cartPanel;

    public RewardsController() {

    }

    public void setData(RewardsAccount account, Customer customer) {
        this.account = account;
        this.customer = customer;
        updateUI();
    }

    /** Setters and Getters **/

    public void setAccount(RewardsAccount account) {
        this.account = account;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RewardsAccount getAccount() {
        return account;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    protected VBox getCartPanel() { return cartPanel; }

    public int viewStarBalance() {
        return account.getTotalStars();
    }

    public void applyRewards(Reward reward) {
        if(reward.getStarCost() <= account.getTotalStars()) {
            account.setTotalStars(account.getTotalStars() - reward.getStarCost());
            reward.setRedeemed(true);
            updateTier();
        }
    }

    public List<Reward> checkOffers() {
        return account.getOffers();
    }

    public void updateTier() {
        if(account.getTotalStars() >= 0 && account.getTotalStars() <= 500) {
            account.setTier("Green");
        } else if(account.getTotalStars() > 500 && account.getTotalStars() <= 2500) {
            account.setTier("Gold");
        } else {
            account.setTier("Reserve");
        }
    }

    public void earnStars(Order order) {
        int starsEarned = (int) (order.getTotalPrice() * 2);
        account.setTotalStars(account.getTotalStars() + starsEarned);
        updateTier();
    }

    @FXML
    public void cartButtonClick(ActionEvent event) {
        toggleCart();
    }

    /** opens the application **/
    public Application getApplication() {
        return null;
    }

    /** sets the scene for the rewards screen **/
    public Scene rewardsScreen() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUI() {
        welcomeLabel.setText("Welcome, " + customer.getName());

        int stars = account.getTotalStars();
        starBalanceLabel.setText(stars + " stars");
        updateTier();

        String tier = account.getTier();
        if (tier.equals("Green")) {
            tierProgressBar.setProgress(stars / 500.0);
            tierGoalLabel.setText((500 - stars) + " stars to Gold");
        } else if (tier.equals("Gold")) {
            tierProgressBar.setProgress((stars - 500) / 2000.0);
            tierGoalLabel.setText((2500 - stars) + " stars to Reserve");
        } else {
            tierProgressBar.setProgress(1.0);
            tierGoalLabel.setText("Reserve tier reached!");
        }

        howitWorksArea.setText("1. Pay with the app to earn 2 stars per $1.\n2. Redeem stars for free drinks and food.\n3. Green: 0-499 stars | Gold: 500-2499 | Reserve: 2500+");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order View.fxml"));
        Parent root = loader.load();
        OrderController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
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

    @FXML
    public void initialize() {
        initCart();
    }
}
