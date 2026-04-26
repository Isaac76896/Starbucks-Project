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

    public List<User> getMenuItems() {
        return menuItems;
    }

    public List<User> getCategories() {
        return categories;
    }

    public static void loadMenu() {}

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
