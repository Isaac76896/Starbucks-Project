import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private List<OrderItem> items;
    private String status;
    private double totalPrice;
    private Date timeStamp;

    public Order(String orderId, List<OrderItem> items, String status, double totalPrice, Date timeStamp) {
        this.orderId = orderId;
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
        this.timeStamp = timeStamp;
    }

    /** Setters and Getters **/

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public double calctotal() {
        return 0;
    }
}
