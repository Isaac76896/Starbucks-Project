package com.example.starbucksproject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

final class SceneNavigator {
    static final double DEFAULT_WIDTH = 1024;
    static final double DEFAULT_HEIGHT = 700;

    private SceneNavigator() {
    }

    static Scene createScene(Parent root) {
        prepareRoot(root);
        return new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    static void setScene(Stage stage, Parent root) {
        prepareRoot(root);
        Scene currentScene = stage.getScene();
        double width = currentScene == null ? DEFAULT_WIDTH : currentScene.getWidth();
        double height = currentScene == null ? DEFAULT_HEIGHT : currentScene.getHeight();
        stage.setScene(new Scene(root, width, height));
    }

    static void setScene(Stage stage, Scene scene) {
        setScene(stage, scene.getRoot());
    }

    private static void prepareRoot(Parent root) {
        if (root instanceof Region region) {
            region.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }
}
