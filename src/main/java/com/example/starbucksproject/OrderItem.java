package com.example.starbucksproject;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private List<Customization> customizations;
    private double subtotal;

    public OrderItem(MenuItem menuItem, int quantity)
    {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.customizations = new ArrayList<>();
        this.subtotal = 0.0;
    }

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

    public void addCustomization(Customization customization)
    {
        customizations.add(customization);
    }

    public double calcSubtotal() {
        double customizationCost = 0.0;

        for (Customization customization : customizations) {
            customizationCost += customization.getExtraCost();
        }

        subtotal = (menuItem.getPrice() + customizationCost) * quantity;
        return subtotal;
    }
}