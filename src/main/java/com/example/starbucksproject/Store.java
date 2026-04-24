package com.example.starbucksproject;

import java.util.List;

public class Store {
    private String storeId;
    private String address;
    private String hours;
    private double[] coordinates;
    private List<Order> amenities;

    public Store(String storeId, String address, String hours, double[] coordinates, List<Order> amenities) {
        this.storeId = storeId;
        this.address = address;
        this.hours = hours;
        this.coordinates = coordinates;
        this.amenities = amenities;
    }

    /** Setters and Getters **/

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setAmenities(List<Order> amenities) {
        this.amenities = amenities;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public List<Order> getAmenities() {
        return amenities;
    }

    private double DistanceTo() {
        return 0.0;
    }
}
