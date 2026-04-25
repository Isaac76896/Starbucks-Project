package com.example.starbucksproject;


import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class StoreController {
    private List<Store> stores;
    private double[] userLocation;

    @FXML private TextField searchField;
    @FXML private Button findNearbyButton;
    @FXML private VBox storeCardsBox;
    @FXML private Label mapPlaceholderLabel;
    @FXML private Button menuButton;
    @FXML private Button locationsButton;
    @FXML private Button rewardsButton;
    @FXML private Button giftCardsButton;
    @FXML private Button cartButton;

    public StoreController(List<Store> stores, double[] userLocation) {
        this.stores = stores;
        this.userLocation = userLocation;
    }

    public void setStores(List<Store> stores) { this.stores = stores; }
    public void setUserLocation(double[] userLocation) { this.userLocation = userLocation; }
    public List<Store> getStores() { return stores; }
    public double[] getUserLocation() { return userLocation; }

    public List<Store> findNearby() {
        return stores.stream()
                .sorted(Comparator.comparingDouble(s -> s.distanceTo(userLocation)))
                .collect(Collectors.toList());
    }

    public Store getStoreByDetails(String storeId) {
        return stores.stream()
                .filter(s -> s.getStoreId().equals(storeId))
                .findFirst()
                .orElse(null);
    }

    public List<Store> filterByAmenity(String amenity) {
        return stores.stream()
                .filter(s -> s.getAmenities() != null
                        && s.getAmenities().stream()
                        .anyMatch(a -> a.equalsIgnoreCase(amenity)))
                .collect(Collectors.toList());
    }

    public String getDirections(String storeId) {
        Store store = getStoreByDetails(storeId);
        if (store == null) return "Store not found.";

        StringBuilder sb = new StringBuilder();
        sb.append("Directions to: ").append(store.getAddress()).append("\n");
        if (store.getCoordinates() != null && store.getCoordinates().length >= 2) {
            sb.append("Coordinates: ")
                    .append(store.getCoordinates()[0]).append(", ")
                    .append(store.getCoordinates()[1]).append("\n");
        }
        sb.append("Hours: ").append(store.getHours()).append("\n");

        if (userLocation != null && userLocation.length >= 2) {
            double dist = store.distanceTo(userLocation);
            sb.append(String.format("Distance: %.1f km", dist));
        }
        return sb.toString();
    }

    public Scene storeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreScreen.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void initialize() {
        displayStoreCards(stores);

        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> onSearchChanged(newVal));
    }

    private List<Store> currentDisplayedStores = new ArrayList<>();

    private void displayStoreCards(List<Store> storeList) {
        currentDisplayedStores = storeList;
        storeCardsBox.getChildren().clear();

        for (int i = 0; i < storeList.size(); i++) {
            Store store = storeList.get(i);
            storeCardsBox.getChildren().add(createStoreCard(store, i + 1));
        }

        if (storeList.isEmpty()) {
            Label empty = new Label("No stores found.");
            empty.setStyle("-fx-text-fill: #999; -fx-font-size: 13;");
            storeCardsBox.getChildren().add(empty);
        }

        mapPlaceholderLabel.setText("Location displayed on map");
    }

    private VBox createStoreCard(Store store, int index) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(12, 14, 12, 14));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-cursor: hand;");

        Label nameLabel = new Label(store.getAddress());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #1E3932;");

        Label hoursLabel = new Label(store.getHours());
        hoursLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");

        if (store.getAmenities() != null && !store.getAmenities().isEmpty()) {
            Label amenitiesLabel = new Label(String.join(" · ", store.getAmenities()));
            amenitiesLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #999;");
            amenitiesLabel.setWrapText(true);
            card.getChildren().addAll(nameLabel, hoursLabel, amenitiesLabel);
        } else {
            card.getChildren().addAll(nameLabel, hoursLabel);
        }

        card.setOnMouseClicked(e -> onStoreCardClicked(store));

        card.setOnMouseEntered(e ->
                card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10; " +
                        "-fx-border-color: #00704A; -fx-border-radius: 10; -fx-cursor: hand;"));
        card.setOnMouseExited(e ->
                card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                        "-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-cursor: hand;"));

        return card;
    }

    private void onStoreCardClicked(Store store) {
        StringBuilder sb = new StringBuilder();
        sb.append(store.getAddress()).append("\n");
        sb.append(store.getHours());
        if (store.getCoordinates() != null && store.getCoordinates().length >= 2) {
            sb.append("\n").append(String.format("%.4f, %.4f",
                    store.getCoordinates()[0], store.getCoordinates()[1]));
        }
        if (userLocation != null && userLocation.length >= 2) {
            double dist = store.distanceTo(userLocation);
            sb.append(String.format("\n%.1f km away", dist));
        }
        mapPlaceholderLabel.setText(sb.toString());
    }

    @FXML
    private void onFindNearbyClicked() {
        displayStoreCards(findNearby());
    }

    private void onSearchChanged(String query) {
        if (query == null || query.trim().isEmpty()) {
            displayStoreCards(stores);
        } else {
            String lower = query.toLowerCase();
            List<Store> filtered = stores.stream()
                    .filter(s -> s.getAddress().toLowerCase().contains(lower)
                            || s.getStoreId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
            displayStoreCards(filtered);
        }
    }
}