module com.example.starbucksproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.starbucksproject to javafx.fxml;
    exports com.example.starbucksproject;
}