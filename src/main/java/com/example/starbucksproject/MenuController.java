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

    public static List<User> filterByCategory() {
        return null;
    }

    public static List<User> searchItems() {
        return null;
    }

    public static MenuItem getItemDetails() {
        return null;
    }
}
