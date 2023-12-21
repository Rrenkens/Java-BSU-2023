module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;
    requires kotlin.stdlib;

    opens by.lenson423.paint to javafx.fxml;
    exports by.lenson423.paint;
}