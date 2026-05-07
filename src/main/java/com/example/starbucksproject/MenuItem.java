package com.example.starbucksproject;
public class MenuItem {

    private String itemId;
    private String name;
    private double price;
    private String category;
    private String subcategory;
    private String description;
    private String sizeOptions;
    private String priceRange;
    private String caloriesRange;
    private String available;
    private String imageUrl;

    public MenuItem(String itemId, String name, double price, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public MenuItem(String itemId, String category, String subcategory, String name,
                    String description, String sizeOptions, String priceRange,
                    String caloriesRange, String available) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.sizeOptions = sizeOptions;
        this.priceRange = priceRange;
        this.caloriesRange = caloriesRange;
        this.available = available;

        try {
            String low = priceRange.split("-")[0];
            this.price = Double.parseDouble(low);
        } catch (Exception e) {
            this.price = 0.0;
        }
    }

    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append("Category: ").append(category);
        if (subcategory != null) {
            sb.append(" > ").append(subcategory);
        }
        sb.append("\n");
        if (description != null) {
            sb.append("Description: ").append(description).append("\n");
        }
        if (sizeOptions != null) {
            sb.append("Sizes: ").append(sizeOptions).append("\n");
        }
        if (priceRange != null) {
            sb.append("Price: $").append(priceRange).append("\n");
        } else {
            sb.append("Price: $").append(String.format("%.2f", price)).append("\n");
        }
        if (caloriesRange != null) {
            sb.append("Calories: ").append(caloriesRange).append("\n");
        }
        if (available != null) {
            sb.append("Availability: ").append(available).append("\n");
        }
        return sb.toString();
    }

    public void setItemId(String itemId) { this.itemId = itemId; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
    public void setDescription(String description) { this.description = description; }
    public void setSizeOptions(String sizeOptions) { this.sizeOptions = sizeOptions; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
    public void setCaloriesRange(String caloriesRange) { this.caloriesRange = caloriesRange; }
    public void setAvailable(String available) { this.available = available; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public String getDescription() { return description; }
    public String getSizeOptions() { return sizeOptions; }
    public String getPriceRange() { return priceRange; }
    public String getCaloriesRange() { return caloriesRange; }
    public String getAvailable() { return available; }

    @Override
    public String toString() {
        if (priceRange != null) {
            return name + " — $" + priceRange;
        }
        return name + " — $" + String.format("%.2f", price);
    }
}