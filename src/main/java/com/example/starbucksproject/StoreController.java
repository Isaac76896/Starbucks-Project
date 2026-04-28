package com.example.starbucksproject;


import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class StoreController extends BaseController {
    private List<Store> stores;
    private double[] userLocation;
    private Customer customer;
    private RewardsAccount account;

    @FXML private TextField searchField;
    @FXML private Button findNearbyButton;
    @FXML private VBox storeCardsBox;
    @FXML private Label mapPlaceholderLabel;
    @FXML private Button menuButton;
    @FXML private Button locationsButton;
    @FXML private Button rewardsButton;
    @FXML private Button giftCardsButton;
    @FXML private Button cartButton;
    @FXML private VBox cartPanel;

    public StoreController() {
        this.stores = new ArrayList<>();
        this.userLocation = new double[]{0,0};
    }

    public void setStores(List<Store> stores) { this.stores = stores; }
    public void setUserLocation(double[] userLocation) { this.userLocation = userLocation; }
    public List<Store> getStores() { return stores; }
    public double[] getUserLocation() { return userLocation; }

    @Override
    protected VBox getCartPanel() { return cartPanel; }

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
            return SceneNavigator.createScene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void cartButtonClick(ActionEvent event) {
        toggleCart();
    }

    @FXML
    public void initialize() {
        initCart();
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
            Label empty = new Label("No stores.csv found.");
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

    private void loadStores() {
        stores = new ArrayList<>();
        stores.add(new Store("SA001", "17619 La Cantera Pkwy, San Antonio, TX 78257", "Mon-Sun: 5:30AM - 10:00PM",
                new double[]{29.6005, -98.6097}, Arrays.asList("Drive-Thru", "Mobile Order", "WiFi")));
        stores.add(new Store("SA002", "255 E Basse Rd, San Antonio, TX 78209", "Mon-Sun: 6:00AM - 9:00PM",
                new double[]{29.4871, -98.4586}, Arrays.asList("Dine-In", "WiFi", "Mobile Order")));
        stores.add(new Store("SA003", "7822 Culebra Rd, San Antonio, TX 78251", "Mon-Sun: 5:00AM - 10:00PM",
                new double[]{29.4985, -98.6401}, Arrays.asList("Drive-Thru", "Mobile Order")));
        stores.add(new Store("SA004", "3030 Thousand Oaks Dr, San Antonio, TX 78247", "Mon-Sun: 6:00AM - 9:30PM",
                new double[]{29.5852, -98.3901}, Arrays.asList("Drive-Thru", "WiFi", "Dine-In")));
        stores.add(new Store("SA005", "1819 N Loop 1604 W, San Antonio, TX 78248", "Mon-Sun: 5:30AM - 10:30PM",
                new double[]{29.6101, -98.5234}, Arrays.asList("Drive-Thru", "Mobile Order", "WiFi")));
        stores.add(new Store("SA006", "6750 W Loop 1604 N, San Antonio, TX 78254", "Mon-Sun: 5:00AM - 11:00PM",
                new double[]{29.5723, -98.6789}, Arrays.asList("Drive-Thru", "Dine-In", "WiFi")));
        stores.add(new Store("SA007", "100 Alamo Plaza, San Antonio, TX 78205", "Mon-Sun: 6:00AM - 8:00PM",
                new double[]{29.4260, -98.4861}, Arrays.asList("Dine-In", "WiFi")));
        stores.add(new Store("SA008", "4522 Fredericksburg Rd, San Antonio, TX 78201", "Mon-Sun: 5:30AM - 9:00PM",
                new double[]{29.4712, -98.5341}, Arrays.asList("Drive-Thru", "Mobile Order")));
    }

    @FXML
    private void onFindNearbyClicked() {
        if (userLocation == null || (userLocation[0] == 0 && userLocation[1] == 0)) {
            userLocation = new double[]{29.4241, -98.4936}; // downtown San Antonio
        }
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

    @FXML
    public void menuButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu Screen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MenuController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void orderButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order View.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        OrderController controller = loader.getController();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void giftCardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gift Cards.fxml"));
        Parent root = loader.load();
        GiftCardController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void locationsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store Screen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        StoreController controller = loader.getController();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    @FXML
    public void rewardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
        Parent root = loader.load();
        RewardsController controller = loader.getController();
        controller.setData(AppState.getInstance().getAccount(), AppState.getInstance().getCustomer());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneNavigator.setScene(stage, root);
        stage.show();
    }

    public void setAccount(RewardsAccount account) {
        this.account = account;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
