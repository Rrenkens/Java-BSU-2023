package by.MikhailShurov.paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PaintApp extends Application {
    private static final int CANVAS_WIDTH = 1000;
    private static final int CANVAS_HEIGHT = 1000;

    private final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private ColorPicker colorPicker;
    private Slider thicknessSlider;

    private double lastX;
    private double lastY;
    ComboBox<Mode> modeComboBox;
    WritableImage img;

    private final FileChooser fileChooser = new FileChooser();

    enum Mode {
        Free, Rectangle, Circle
    }

    Mode mode = Mode.Free;

    public static void main(String[] args) {
        launch(args);
    }

    private void drawRectangle(double x1, double y1, double x2, double y2, GraphicsContext currentGc) {
        currentGc.strokeRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

    private void drawCircle(double x1, double y1, double x2, double y2, GraphicsContext currentGc) {
        double radius = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        currentGc.strokeOval(x1 - radius, y1 - radius, radius * 2, radius * 2);
    }

    private void drawLine(double endX, double endY) {
        gc.lineTo(endX, endY);
        gc.stroke();
    }

    EventHandler<MouseEvent> mousePressedHandler = event -> {
        img = canvas.snapshot(null, null);
        lastX = event.getX();
        lastY = event.getY();

        gc.beginPath();
        gc.setStroke(colorPicker.getValue());
        gc.setFill(colorPicker.getValue());
        gc.setLineWidth(thicknessSlider.getValue());
    };

    EventHandler<MouseEvent> mouseDraggedHandler = e -> {
        if (mode == Mode.Free) {
            drawLine(e.getX(), e.getY());
        } else if (mode == Mode.Rectangle) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
            drawRectangle(lastX, lastY, e.getX(), e.getY(), gc);
        } else if (mode == Mode.Circle) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
            drawCircle(lastX, lastY, e.getX(), e.getY(), gc);
        }
    };

    EventHandler<MouseEvent> mouseReleasedHandler = e -> {
        if (mode == Mode.Rectangle) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
            drawRectangle(lastX, lastY, e.getX(), e.getY(), gc);
        } else if (mode == Mode.Circle) {
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
            drawCircle(lastX, lastY, e.getX(), e.getY(), gc);
        }
    };

    private void saveImageToFile() {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null), "png", file);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private void loadImageFromFile() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String path = file.toURI().toString();
            Image tmp = new javafx.scene.image.Image(path);
            gc.drawImage(tmp, 0, 0);
        }
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        colorPicker = new ColorPicker(Color.BLACK);

        thicknessSlider = new Slider(1,10, 2);
        thicknessSlider.setShowTickMarks(true);
        thicknessSlider.setShowTickLabels(true);
        thicknessSlider.setMajorTickUnit(1);
        thicknessSlider.setBlockIncrement(1);
        thicknessSlider.setPrefWidth(150);

        canvas.setOnMousePressed(mousePressedHandler);
        canvas.setOnMouseDragged(mouseDraggedHandler);
        canvas.setOnMouseReleased(mouseReleasedHandler);

        modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll(Mode.Free, Mode.Rectangle, Mode.Circle);
        modeComboBox.setValue(Mode.Free);
        modeComboBox.setOnAction(e -> mode = modeComboBox.getValue());

        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(e -> saveImageToFile());

        Button loadButton = new Button("Загрузить");
        loadButton.setOnAction(e -> loadImageFromFile());

        Button clearButton = new Button("Очистить");
        clearButton.setOnAction(e -> clearCanvas());

        HBox controlsBox = new HBox(10);
        controlsBox.setPadding(new Insets(10));
        controlsBox.getChildren().addAll(colorPicker, thicknessSlider, modeComboBox, saveButton, loadButton, clearButton);

        BorderPane root = new BorderPane();
        root.setTop(controlsBox);
        root.setCenter(canvas);

        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Paint");
        primaryStage.show();
    }
}