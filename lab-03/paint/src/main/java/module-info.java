module by.bvr_julia.paint {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;

    opens by.bvr_julia.paint to javafx.fxml;
    exports by.bvr_julia.paint;
}