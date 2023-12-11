module com.example.lab03 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens by.Lenson423.paint to javafx.fxml;
    exports by.Lenson423.paint;
}