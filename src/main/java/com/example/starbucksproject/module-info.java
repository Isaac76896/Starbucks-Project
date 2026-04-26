module com.example.starbucksproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires org.kordamp.ikonli.core;


    opens com.example.starbucksproject to javafx.fxml;
    exports com.example.starbucksproject;
}