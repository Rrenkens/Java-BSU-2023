package by.nrydo.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Paint extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Paint");
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("paint.fxml")))));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
