package com.example.starbucksproject;


import java.util.*;

public class Store {
    private String storeId;
    private String address;
    private String hours;
    private double[] coordinates;
    private List<String> amenities;

    public Store(String storeId, String address, String hours, double[] coordinates, List<String> amenities) {
        this.storeId = storeId;
        this.address = address;
        this.hours = hours;
        this.coordinates = coordinates;
        this.amenities = amenities;
    }

    public void setStoreId(String storeId) { this.storeId = storeId; }
    public void setAddress(String address) { this.address = address; }
    public void setHours(String hours) { this.hours = hours; }
    public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public String getStoreId() { return storeId; }
    public String getAddress() { return address; }
    public String getHours() { return hours; }
    public double[] getCoordinates() { return coordinates; }
    public List<String> getAmenities() { return amenities; }

    public double distanceTo(double[] userLocation) {
        if (coordinates == null || userLocation == null
                || coordinates.length < 2 || userLocation.length < 2) {
            return Double.MAX_VALUE;
        }

        final double R = 6371.0;
        double lat1 = Math.toRadians(coordinates[0]);
        double lat2 = Math.toRadians(userLocation[0]);
        double dLat = Math.toRadians(userLocation[0] - coordinates[0]);
        double dLon = Math.toRadians(userLocation[1] - coordinates[1]);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    @Override
    public String toString() {
        return address + " (" + hours + ")";
    }
}