package com.example.starbucksproject;

import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class MenuController {
    private List<MenuItem> menuItems;
    private List<String> categories;
    private Customer customer;
    private RewardsAccount account;
    private List<MenuItem> cartItems = new ArrayList<>();
    private double cartTotal = 0.0;

    @FXML private Label categoryHeadingLabel;
    @FXML private HBox filterButtonsBox;
    @FXML private TextField searchField;
    @FXML private FlowPane itemCardGrid;
    @FXML private Button menuButton;
    @FXML private Button locationsButton;
    @FXML private Button rewardsButton;
    @FXML private Button giftCardsButton;
    @FXML private Button cartButton;
    @FXML private VBox cartPanel;

    private String activeCategory = null;
    private String activeSubcategory = null;

    public MenuController() {}

    public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<MenuItem> getMenuItems() { return menuItems; }
    public List<String> getCategories() { return categories; }

    public void loadMenu() {
        menuItems = new ArrayList<>();
        categories = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/starbucks_menu.csv"))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = parseCsvLine(line);
                if (fields.length < 9) continue;
                MenuItem item = new MenuItem(
                        fields[0].trim(), fields[1].trim(), fields[2].trim(),
                        fields[3].trim(), fields[4].trim(), fields[5].trim(),
                        fields[6].trim(), fields[7].trim(), fields[8].trim()
                );
                menuItems.add(item);
                String cat = fields[1].trim();
                if (!categories.contains(cat)) categories.add(cat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    public List<MenuItem> filterByCategory(String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category)
                        || (item.getSubcategory() != null
                        && item.getSubcategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }

    public List<MenuItem> searchItems(String query) {
        String lower = query.toLowerCase();
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(lower)
                        || (item.getDescription() != null
                        && item.getDescription().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }

    public MenuItem getItemDetails(String itemId) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    @FXML
    public void initialize() {
        loadMenu();
        buildCategoryTabs();
        displayItems(menuItems);
        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> onSearchChanged(newVal));
    }

    private void buildCategoryTabs() {
        Button allBtn = createFilterTab("All", true);
        allBtn.setOnAction(e -> {
            activeCategory = null;
            activeSubcategory = null;
            categoryHeadingLabel.setText("Menu");
            buildSubcategoryTabs(null);
            displayItems(menuItems);
        });
        filterButtonsBox.getChildren().add(allBtn);

        for (String cat : categories) {
            Button btn = createFilterTab(cat, false);
            btn.setOnAction(e -> {
                activeCategory = cat;
                activeSubcategory = null;
                categoryHeadingLabel.setText(cat);
                buildSubcategoryTabs(cat);
                displayItems(filterByCategory(cat));
            });
            filterButtonsBox.getChildren().add(btn);
        }
    }

    private void buildSubcategoryTabs(String category) {
        filterButtonsBox.getChildren().clear();

        Button allBtn = createFilterTab("All", category == null);
        allBtn.setOnAction(e -> {
            activeCategory = null;
            activeSubcategory = null;
            categoryHeadingLabel.setText("Menu");
            buildSubcategoryTabs(null);
            displayItems(menuItems);
        });
        filterButtonsBox.getChildren().add(allBtn);

        for (String cat : categories) {
            Button btn = createFilterTab(cat, cat.equals(category));
            btn.setOnAction(e -> {
                activeCategory = cat;
                activeSubcategory = null;
                categoryHeadingLabel.setText(cat);
                buildSubcategoryTabs(cat);
                displayItems(filterByCategory(cat));
            });
            filterButtonsBox.getChildren().add(btn);
        }

        if (category != null) {
            List<String> subcats = menuItems.stream()
                    .filter(item -> item.getCategory().equalsIgnoreCase(category))
                    .map(MenuItem::getSubcategory)
                    .filter(s -> s != null && !s.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());

            if (!subcats.isEmpty()) {
                Region spacer = new Region();
                spacer.setPrefWidth(10);
                filterButtonsBox.getChildren().add(spacer);

                for (String sub : subcats) {
                    Button subBtn = createFilterTab(sub, sub.equals(activeSubcategory));
                    subBtn.setOnAction(e -> {
                        activeSubcategory = sub;
                        categoryHeadingLabel.setText(sub);
                        buildSubcategoryTabs(category);
                        displayItems(filterByCategory(sub));
                    });
                    filterButtonsBox.getChildren().add(subBtn);
                }
            }
        }
    }

    private Button createFilterTab(String text, boolean active) {
        Button btn = new Button(text);
        if (active) {
            btn.setStyle("-fx-background-color: #1E3932; -fx-text-fill: white; " +
                    "-fx-background-radius: 20; -fx-padding: 6 16; -fx-font-size: 12; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: white; -fx-text-fill: #1E3932; " +
                    "-fx-border-color: #ccc; -fx-border-radius: 20; -fx-background-radius: 20; " +
                    "-fx-padding: 6 16; -fx-font-size: 12; -fx-cursor: hand;");
        }
        return btn;
    }

    private List<MenuItem> currentDisplayedItems = new ArrayList<>();

    private void displayItems(List<MenuItem> items) {
        currentDisplayedItems = items;
        itemCardGrid.getChildren().clear();
        for (MenuItem item : items) {
            itemCardGrid.getChildren().add(createItemCard(item));
        }
    }

    private VBox createItemCard(MenuItem item) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(210);
        card.setPrefHeight(220);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 2);");
        card.setPadding(new Insets(0));

        Region imagePlaceholder = new Region();
        imagePlaceholder.setPrefHeight(120);
        imagePlaceholder.setMinHeight(120);
        imagePlaceholder.setMaxWidth(Double.MAX_VALUE);
        imagePlaceholder.setStyle("-fx-background-color: #1E3932; -fx-background-radius: 12 12 0 0;");

        VBox textArea = new VBox(4);
        textArea.setPadding(new Insets(8, 12, 10, 12));
        textArea.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(item.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-size: 13; -fx-font-weight: bold; -fx-text-fill: #1E3932;");
        nameLabel.setMaxWidth(180);

        Label priceLabel = new Label(item.getPriceRange() != null ?
                "$" + item.getPriceRange() : "$" + String.format("%.2f", item.getPrice()));
        priceLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #666;");

        Button addToOrderBtn = new Button("Add to Order");
        addToOrderBtn.setStyle("-fx-background-color: #00704A; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 4 16; -fx-font-size: 11; -fx-cursor: hand;");
        addToOrderBtn.setOnAction(e -> addToCart(item));

        textArea.getChildren().addAll(nameLabel, priceLabel, addToOrderBtn);
        card.getChildren().addAll(imagePlaceholder, textArea);
        return card;
    }

    private void addToCart(MenuItem item) {
        cartItems.add(item);
        if (item.getPrice() > 0) cartTotal += item.getPrice();
        cartPanel.setVisible(true);
        cartPanel.setManaged(true);
        updateCartPanel();
    }

    private void removeFromCart(MenuItem item) {
        cartItems.remove(item);
        if (item.getPrice() > 0) cartTotal -= item.getPrice();
        updateCartPanel();
    }

    private void updateCartPanel() {
        cartPanel.getChildren().clear();

        Label title = new Label("Your Order");
        title.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");
        cartPanel.getChildren().add(title);

        if (cartItems.isEmpty()) {
            Label empty = new Label("No items added yet");
            empty.setStyle("-fx-font-size: 13; -fx-text-fill: gray;");
            cartPanel.getChildren().add(empty);
        } else {
            for (MenuItem item : cartItems) {
                HBox row = new HBox(8);
                row.setAlignment(Pos.CENTER_LEFT);

                Label name = new Label(item.getName());
                name.setWrapText(true);
                name.setMaxWidth(140);
                name.setStyle("-fx-font-size: 12;");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Label price = new Label(String.format("$%.2f", item.getPrice()));
                price.setStyle("-fx-font-size: 12;");

                Button removeBtn = new Button("✕");
                removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #cc0000; " +
                        "-fx-cursor: hand; -fx-font-size: 11; -fx-padding: 0 4;");
                removeBtn.setOnAction(e -> removeFromCart(item));

                row.getChildren().addAll(name, spacer, price, removeBtn);
                cartPanel.getChildren().add(row);
            }
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        cartPanel.getChildren().add(spacer);

        Label total = new Label(String.format("Total: $%.2f", cartTotal));
        total.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        cartPanel.getChildren().add(total);

        Button placeOrder = new Button("Place Order");
        placeOrder.setMaxWidth(Double.MAX_VALUE);
        placeOrder.setStyle("-fx-background-color: #1E3932; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-padding: 12; -fx-cursor: hand;");
        placeOrder.setOnAction(e -> placeOrder());
        cartPanel.getChildren().add(placeOrder);
    }

    private void placeOrder() {
        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Cart");
            alert.setHeaderText("Your cart is empty!");
            alert.setContentText("Please add items before placing an order.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Place Order");
        alert.setHeaderText("Confirm your order");
        alert.setContentText(String.format("Total: $%.2f\n\nPlace this order?", cartTotal));

        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                cartItems.clear();
                cartTotal = 0.0;
                updateCartPanel();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Order Placed");
                success.setHeaderText("Order placed successfully!");
                success.setContentText("Your order is being prepared!");
                success.showAndWait();
            }
        });
    }

    private void onSearchChanged(String query) {
        if (query == null || query.trim().isEmpty()) {
            if (activeSubcategory != null) {
                displayItems(filterByCategory(activeSubcategory));
            } else if (activeCategory != null) {
                displayItems(filterByCategory(activeCategory));
            } else {
                displayItems(menuItems);
            }
        } else {
            displayItems(searchItems(query));
        }
    }

    @FXML
    public void menuButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu Screen.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void cartButtonClick(ActionEvent event) {
        boolean showing = cartPanel.isVisible();
        cartPanel.setVisible(!showing);
        cartPanel.setManaged(!showing);
    }

    @FXML
    public void giftCardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Gift Cards.fxml"));
        Parent root = loader.load();
        GiftCardController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void locationsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Store Screen.fxml"));
        Parent root = loader.load();
        StoreController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void rewardsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
        Parent root = loader.load();
        RewardsController controller = loader.getController();
        controller.setData(AppState.getInstance().getAccount(), AppState.getInstance().getCustomer());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setAccount(RewardsAccount account) { this.account = account; }
}