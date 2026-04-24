package com.example.starbucksproject;

import java.util.List;

public class StoreController {
    private List<Store> stores;
    private double[] userLocation;

    public StoreController(List<Store> stores, double[] userLocation) {
        this.stores = stores;
        this.userLocation = userLocation;
    }

    /** Setters and Getters **/

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public void setUserLocation(double[] userLocation) {
        this.userLocation = userLocation;
    }

    public List<Store> getStores() {
        return stores;
    }

    public double[] getUserLocation() {
        return userLocation;
    }

    public List<Store> findNearby() {
        return null;
    }

    public Store getStoreByDetails() {
        return null;
    }

    public List<Store> filterByAmenity() {
        return null;
    }

    public String getDirections() {
        return null;
    }
}
