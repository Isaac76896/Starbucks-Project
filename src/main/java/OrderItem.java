import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private List<Customization> customizations;
    private double subtotal;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.customizations = new ArrayList<>();
        this.subtotal = 0;
    }

    /** Setters and Getters **/

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomizations(List<Customization> customizations) {
        this.customizations = customizations;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Customization> getCustomizations() {
        return customizations;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double calcSubtotal() {
        return menuItem.getPrice() * quantity;
    }
}
