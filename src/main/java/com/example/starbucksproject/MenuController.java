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
    private List<User> menuItems;
    private List<User> categories;

    public MenuController(List<User> menuItems, List<User> categories) {
        this.menuItems = menuItems;
        this.categories = categories;
    }

    /** Setters and Getters **/

    public void setMenuItems(List<User> menuItems) {
        this.menuItems = menuItems;
    }

    public void setCategories(List<User> categories) {
        this.categories = categories;
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
}
