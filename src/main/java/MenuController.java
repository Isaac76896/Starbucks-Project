import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class MenuController {
    private List<MenuItem> menuItems;
    private List<String> categories;

    @FXML private Label categoryHeadingLabel;
    @FXML private HBox filterButtonsBox;
    @FXML private TextField searchField;
    @FXML private FlowPane itemCardGrid;
    @FXML private Button menuButton;
    @FXML private Button locationsButton;
    @FXML private Button rewardsButton;
    @FXML private Button giftCardsButton;
    @FXML private Button cartButton;

    private String activeCategory = null;
    private String activeSubcategory = null;

    public MenuController(List<MenuItem> menuItems, List<String> categories) {
        this.menuItems = menuItems;
        this.categories = categories;
    }

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
                        fields[0].trim(),
                        fields[1].trim(),
                        fields[2].trim(),
                        fields[3].trim(),
                        fields[4].trim(),
                        fields[5].trim(),
                        fields[6].trim(),
                        fields[7].trim(),
                        fields[8].trim()
                );
                menuItems.add(item);

                String cat = fields[1].trim();
                if (!categories.contains(cat)) {
                    categories.add(cat);
                }
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

    public Scene menuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuScreen.fxml"));
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

        Label priceLabel = new Label(item.getPriceRange() != null ? "$" + item.getPriceRange() : "$" + String.format("%.2f", item.getPrice()));
        priceLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #666;");

        Button customizeBtn = new Button("Customize");
        customizeBtn.setStyle("-fx-background-color: #00704A; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 4 16; -fx-font-size: 11; -fx-cursor: hand;");
        customizeBtn.setOnAction(e -> showItemPopup(item));

        textArea.getChildren().addAll(nameLabel, priceLabel, customizeBtn);
        card.getChildren().addAll(imagePlaceholder, textArea);

        return card;
    }

    private void showItemPopup(MenuItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(item.getName());
        alert.setHeaderText(item.getName());
        alert.setContentText(item.getDetails());
        alert.showAndWait();
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
}
