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
