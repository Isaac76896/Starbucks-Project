import java.util.List;

public class RewardsController {
    private RewardsAccount account;
    private Customer customer;

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
        return 0;
    }

    public void applyRewards() {}

    public List<Order> checkOffers() {
        return null;
    }

    public void updateTier() {}

    public void earnStars() {}
}
