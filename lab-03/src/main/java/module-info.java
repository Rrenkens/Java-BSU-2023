module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens by.MikhailShurov.paint to javafx.fxml;
    exports by.MikhailShurov.paint;
}