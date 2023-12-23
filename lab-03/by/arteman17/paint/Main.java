package by.arteman17.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller();

        stage.setResizable(false);
        Scene scene = new Scene(controller.getFrame(), 1920, 900);

        stage.setTitle("MyPaint");
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
}
