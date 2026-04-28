package com.example.starbucksproject;


import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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
    @FXML private StackPane mapArea;

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
        loadStores();
        displayStoreCards(stores);
        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> onSearchChanged(newVal));
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

    private void loadStores() {
        stores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/stores.csv"))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (fields.length < 7) continue;
                String storeId = fields[0].trim();
                String address = fields[1].trim();
                String hours = fields[2].trim();
                double lat = Double.parseDouble(fields[3].trim());
                double lon = Double.parseDouble(fields[4].trim());
                List<String> amenities = Arrays.asList(
                        fields[5].trim().replaceAll("\"", "").split(","));
                String imageUrl = fields[6].trim();
                stores.add(new Store(storeId, address, hours,
                        new double[]{lat, lon}, amenities, imageUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox createStoreCard(Store store, int index) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(0, 0, 12, 0));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-border-color: #e0e0e0; -fx-border-radius: 10; -fx-cursor: hand;");

        // Image
        if (store.getImageUrl() != null && !store.getImageUrl().isEmpty()) {
            try {
                ImageView imageView = new ImageView(new Image(store.getImageUrl(), 280, 120, true, true));
                imageView.setFitWidth(280);
                imageView.setFitHeight(120);
                imageView.setStyle("-fx-background-radius: 10 10 0 0;");
                card.getChildren().add(imageView);
            } catch (Exception e) {
                // if image fails to load, skip it
            }
        }

        // Text content
        VBox textArea = new VBox(4);
        textArea.setPadding(new Insets(10, 14, 10, 14));

        Label nameLabel = new Label(store.getAddress());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #1E3932;");

        Label hoursLabel = new Label(store.getHours());
        hoursLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #666;");

        textArea.getChildren().addAll(nameLabel, hoursLabel);

        if (store.getAmenities() != null && !store.getAmenities().isEmpty()) {
            Label amenitiesLabel = new Label(String.join(" · ", store.getAmenities()));
            amenitiesLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #999;");
            amenitiesLabel.setWrapText(true);
            textArea.getChildren().add(amenitiesLabel);
        }

        card.getChildren().add(textArea);

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
        mapArea.getChildren().clear();

        VBox infoCard = new VBox(12);
        infoCard.setAlignment(Pos.CENTER_LEFT);
        infoCard.setMaxWidth(400);
        infoCard.setPadding(new Insets(32));
        infoCard.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 12, 0, 0, 4);");

        Label nameLabel = new Label(store.getAddress());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #1E3932;");

        Label hoursLabel = new Label("🕐 " + store.getHours());
        hoursLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #444;");

        if (userLocation != null && userLocation[0] != 0) {
            double dist = store.distanceTo(userLocation);
            Label distLabel = new Label(String.format("📍 %.1f km away", dist));
            distLabel.setStyle("-fx-font-size: 13; -fx-text-fill: #666;");
            infoCard.getChildren().add(distLabel);
        }

        if (store.getAmenities() != null && !store.getAmenities().isEmpty()) {
            HBox amenitiesBox = new HBox(8);
            for (String amenity : store.getAmenities()) {
                Label chip = new Label(amenity);
                chip.setStyle("-fx-background-color: #e8f5e9; -fx-text-fill: #1E3932; " +
                        "-fx-background-radius: 12; -fx-padding: 4 10; -fx-font-size: 11;");
                amenitiesBox.getChildren().add(chip);
            }
            infoCard.getChildren().addAll(nameLabel, hoursLabel, amenitiesBox);
        } else {
            infoCard.getChildren().addAll(nameLabel, hoursLabel);
        }

        Button directionsBtn = new Button("Get Directions");
        directionsBtn.setStyle("-fx-background-color: #1E3932; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 10 24; -fx-font-size: 13; -fx-cursor: hand;");
        directionsBtn.setOnAction(e -> {
            String url = "https://www.google.com/maps/search/?api=1&query=" +
                    store.getAddress().replace(" ", "+");
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        infoCard.getChildren().add(directionsBtn);
        mapArea.getChildren().add(infoCard);
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
