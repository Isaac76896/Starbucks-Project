package com.example.starbucksproject;

public class Customization {
    private String customId;
    private String type;
    private String value;
    private double extraCost;

    public Customization(String customId, String type, String value, double extraCost) {
        this.customId = customId;
        this.type = type;
        this.value = value;
        this.extraCost = extraCost;
    }

    /** Setters and Getters **/
    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(double extraCost) {
        this.extraCost = extraCost;
    }

    public void apply() {}
}
