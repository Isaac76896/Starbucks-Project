import java.util.Date;
import java.util.List;

public class OrderController {
    private Order currentOrder;
    private List<OrderItem> cart;
    private RewardsController rewardsController;

    public OrderController(Order currentOrder, List<OrderItem> cart) {
        this.currentOrder = currentOrder;
        this.cart = cart;
        this.rewardsController = rewardsController;
    }

    /** Setters and Getters **/
    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void setGetCart(List<OrderItem> cart) {
        this.cart = cart;
    }

    public List<OrderItem> getCart() {
        return cart;
    }

    public void addToCart() {}

    public void removeFromCart() {}

    public void customization() {}

    public Order placeOrder() {
        currentOrder.setItems(cart);
        currentOrder.setTotalPrice(currentOrder.calctotal());
        currentOrder.setStatus("PLACED");
        currentOrder.setTimeStamp(new Date());
        rewardsController.earnStars(currentOrder);
        return currentOrder;
    }

    public String trackOrder() {
        return null;
    }
}
