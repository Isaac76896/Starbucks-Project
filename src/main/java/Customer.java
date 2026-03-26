import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String rewardsId;
    private int starBalance;
    private List<Order> orderHistory;
    private List<Order> favoriteItems;

    public Customer(String userId, String name, String email, String passwordHash) {
        super(userId, name, email, passwordHash);
        this.rewardsId = null;
        this.starBalance = 0;
        this.orderHistory = new ArrayList<>();
        this.favoriteItems = new ArrayList<>();
    }

    /** Setters and Getters **/

    public void setRewardsId(String rewardsId) {
        this.rewardsId = rewardsId;
    }

    public void setStarBalance(int starBalance) {
        this.starBalance = starBalance;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void setFavoriteItems(List<Order> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public String getRewardsId() {
        return rewardsId;
    }
    public int getStarBalance() {
        return starBalance;
    }

    public List<Order> getOrderHistry() {
        return orderHistory;
    }

    public List<Order> getFavoriteItems() {
        return favoriteItems;
    }


    public void earnStars() {}

    public void redeemStars() {}
}
