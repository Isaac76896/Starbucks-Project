import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.kordamp.ikonli.Ikonli;

import javax.swing.*;
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
    @FXML Ikonli userIcon;
    @FXML Ikonli coffeeIcon;
    @FXML Ikonli starbucksLogo;
    @FXML Ikonli starbucksStar;
    @FXML TitledPane findAStoreTitle;
    @FXML TitledPane memberPerksTitle;
    @FXML TextArea howitWorksArea;
    @FXML Label starBalanceLabel;
    @FXML ProgressBar starBalanceProgressBar;
    @FXML Label tierGoalLabel;



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

    /** opens the application **/
    public Application getApplication() {
        return null;
    }

    /** sets the scene for the rewards screen **/
    public Scene rewardsScreen() {
        return null;
    }
}
