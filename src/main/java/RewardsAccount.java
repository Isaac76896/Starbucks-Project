import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class RewardsAccount {
    private String accountId;
    private String tier;
    private int totalStars;
    private List<Order> offers;
    private Date expiryDate;

    public RewardsAccount(String accountId, String tier, int totalStars, List<Order> offers, Date expiryDate) {
        this.accountId = accountId;
        this.tier = tier;
        this.totalStars = totalStars;
        this.offers = new ArrayList<>();
        this.expiryDate = new Date();
    }

    /** Setters and Getters **/

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public void setOffers(List<Order> offers) {
        this.offers = offers;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getTier() {
        return tier;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public List<Order> getOffers() {
        return offers;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public List<Order> getAvailRewards() {
        return null;
    }
}
