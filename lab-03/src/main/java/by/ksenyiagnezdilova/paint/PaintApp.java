package by.ksenyiagnezdilova.paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;

public class PaintApp extends Application {
    private double startY;
    private double startX;
    private final Canvas canvas = new Canvas(1980, 1080);
    private final Canvas canvas_second = new Canvas(1980, 1080);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final GraphicsContext gc_second = canvas_second.getGraphicsContext2D();


    private enum DrawMode {
        LINE, RECTANGLE, CIRCLE
    }

    private DrawMode drawMode = DrawMode.LINE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("JavaFX Paint");

        GridPane buttons = new GridPane();


        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        Slider lineWidthSlider = new Slider(1, 10, 2);

        ToggleButton lineButton = new ToggleButton("Line");
        ToggleButton rectangleButton = new ToggleButton("Rectangle");
        ToggleButton circleButton = new ToggleButton("Circle");


        lineButton.setOnAction(e -> drawMode = DrawMode.LINE);

        rectangleButton.setOnAction(e -> drawMode = DrawMode.RECTANGLE);

        circleButton.setOnAction(e -> drawMode = DrawMode.CIRCLE);


        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            gc.beginPath();
            gc.setStroke(colorPicker.getValue());
            gc.setFill(colorPicker.getValue());
            gc.setLineWidth(lineWidthSlider.getValue());

            gc_second.beginPath();
            gc_second.setStroke(colorPicker.getValue());
            gc_second.setFill(colorPicker.getValue());
            gc_second.setLineWidth(lineWidthSlider.getValue());

            if (drawMode == DrawMode.LINE) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (drawMode == DrawMode.LINE) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            } else if (drawMode == DrawMode.RECTANGLE) {
                gc_second.clearRect(0, 0, 1920,1080);
                double endX = e.getX();
                double endY = e.getY();
                double width = Math.abs(endX - startX);
                double height = Math.abs(endY - startY);

                double rectX = Math.min(startX, endX);
                double rectY = Math.min(startY, endY);

                gc_second.strokeRect(rectX, rectY, width, height);
            } else if (drawMode == DrawMode.CIRCLE) {
                gc_second.clearRect(0, 0, 1920,1080);
                double endX = e.getX();
                double endY = e.getY();

                double radius = Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY));

                gc_second.strokeOval(startX - radius, startY - radius, radius * 2, radius * 2);
            }
        });

        canvas.setOnDragDetected(event -> canvas.startFullDrag());

        canvas.setOnMouseDragReleased(e -> {
            if (drawMode == DrawMode.RECTANGLE){
                double endX = e.getX();
                double endY = e.getY();
                double width = Math.abs(endX - startX);
                double height = Math.abs(endY - startY);

                double rectX = Math.min(startX, endX);
                double rectY = Math.min(startY, endY);

                gc.strokeRect(rectX, rectY, width, height);
            } else if (drawMode == DrawMode.CIRCLE) {
                double endX = e.getX();
                double endY = e.getY();

                double radius = Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY));

                gc.strokeOval(startX - radius, startY - radius, radius * 2, radius * 2);
            }
        });

        Button saveButton = new Button("Save Image");
        saveButton.setOnAction(e -> saveImage(canvas));

        Button loadImageButton = new Button("Load Image");
        loadImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                loadImage(selectedFile.getAbsolutePath());
            }
        });

        HBox modeButtons = new HBox(10, lineButton, rectangleButton, circleButton);
        HBox controlBox = new HBox(colorPicker, new HBox(10, lineWidthSlider), saveButton, loadImageButton);

        buttons.add(modeButtons, 0, 1);
        buttons.add(controlBox, 0, 0);
        Separator sep = new Separator(Orientation.HORIZONTAL);
        sep.setMinHeight(10);
        StackPane stackPane = new StackPane(canvas_second, canvas);
        VBox root = new VBox(buttons, sep, stackPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveImage(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImage(String filePath) {
        try {
            Image image = new Image(new FileInputStream(filePath));
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(image, 0, 0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
