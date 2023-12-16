module by.rycbaryana.paint {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.swing;

    opens by.rycbaryana.paint to javafx.fxml;
    exports by.rycbaryana.paint;
}