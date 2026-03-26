import java.util.List;

public class OrderController {
    private Order currentOrder;
    private List<OrderItem> cart;

    public OrderController(Order currentOrder, List<OrderItem> cart) {
        this.currentOrder = currentOrder;
        this.cart = cart;
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
        return null;
    }

    public String trackOrder() {
        return null;
    }
}
