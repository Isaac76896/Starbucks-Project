package com.example.starbucksproject;

public class MenuItem {
    private String itemId;
    private String name;
    private double price;
    private String category;

    public String getDetails() {
        return null;
    }

    /** Setters and Getters for the fields **/
    public void setDetails(String details) {}

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return itemId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public  MenuItem(String itemId, String name, double price, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
