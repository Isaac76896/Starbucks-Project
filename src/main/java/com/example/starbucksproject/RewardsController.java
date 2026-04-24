package com.example.starbucksproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class RewardsController {
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


    public RewardsController(RewardsAccount account, Customer customer) {
        this.account = account;
        this.customer = customer;
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

    //public List<Reward> checkOffers() {
       //return account.getOffers();
  //}

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

    @FXML
    public void initialize() {
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
}
