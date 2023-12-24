module com.example.lab03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens by.fact0rial.paint to javafx.fxml;
    exports by.fact0rial.paint;
}