package com.example.starbucksproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu Screen.fxml"));
        javafx.scene.Parent root = fxmlLoader.load();

        Customer customer = new Customer("user001", "Rain", "rain@email.com", "password");
        RewardsAccount account = new RewardsAccount("ACC001", "Green", 300, new ArrayList<>(), new Date());

        MenuController controller = fxmlLoader.getController();
        controller.setCustomer(customer);
        controller.setAccount(account);

        AppState.getInstance().setCustomer(customer);
        AppState.getInstance().setAccount(account);

        Scene scene = new Scene(root, 1024, 700);
        stage.setTitle("Starbucks");
        stage.setScene(scene);
        stage.show();
    }
}
