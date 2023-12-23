package by.arteman17.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    private final ColorPicker penColorPicker;
    private final Button clearButton, saveButton, openButton;
    private final ComboBox<String> shapeComboBox;
    private final ComboBox<Double> sizeComboBox;
    private final Canvas mainFrame, backFrame;
    private final GraphicsContext main;
    private final GraphicsContext back;
    private final VBox frame;
    private double startX;
    private double startY;

    public Controller() {
        mainFrame = new Canvas(1920, 900);
        backFrame = new Canvas(1920, 900);

        main = mainFrame.getGraphicsContext2D();
        back = backFrame.getGraphicsContext2D();

        penColorPicker = new ColorPicker(Color.BLACK);
        penColorPicker.setPrefSize(150, 50);

        clearButton = new Button("Clear");
        clearButton.setPrefSize(150, 50);
        clearButton.setOnAction(event -> {
            main.clearRect(0, 0, 1920, 900);
            back.clearRect(0, 0, 1920, 900);
        });

        sizeComboBox = new ComboBox<>();
        sizeComboBox.setPrefSize(150, 50);
        sizeComboBox.getItems().addAll(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
                10.0, 12.0, 14.0, 16.0, 18.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0, 50.0);
        sizeComboBox.setValue(2.0);

        shapeComboBox = new ComboBox<>();
        shapeComboBox.setPrefSize(150, 50);
        shapeComboBox.getItems().addAll("Freehand", "Line", "Rectangle", "Ellipse", "Circle", "Erase");
        shapeComboBox.setValue("Freehand");

        saveButton = new Button("Save");
        saveButton.setPrefSize(150, 50);
        saveButton.setOnAction(event -> saveEvent());

        openButton = new Button("Open");
        openButton.setPrefSize(150, 50);
        openButton.setOnAction(event -> openEvent());

        HBox toolBar_ = new HBox(saveButton, openButton, penColorPicker, sizeComboBox, shapeComboBox, clearButton);
        StackPane stackPane = new StackPane(backFrame, mainFrame);
        frame = new VBox(toolBar_, stackPane);

        mainFrame.setOnMousePressed(mouseEvent -> {
            startX = mouseEvent.getX();
            startY = mouseEvent.getY();

            main.beginPath();
            main.setStroke(penColorPicker.getValue());
            main.setLineWidth(sizeComboBox.getValue());

            back.beginPath();
            back.setStroke(penColorPicker.getValue());
            back.setLineWidth(sizeComboBox.getValue());
        });

        mainFrame.setOnMouseDragged(mouseEvent -> {
            back.clearRect(0, 0, 1920, 900);
            mainFrame.toBack();
            paintEvent(mouseEvent, back);
        });

        mainFrame.setOnMouseReleased(mouseEvent -> {
            mainFrame.toFront();
            paintEvent(mouseEvent, main);
        });
    }

    void paintEvent(MouseEvent mouseEvent, GraphicsContext graphicsContext) {
        if (Objects.equals(shapeComboBox.getValue(), "Freehand")) {
            main.lineTo(mouseEvent.getX(), mouseEvent.getY());
            main.stroke();
        } else if (Objects.equals(shapeComboBox.getValue(), "Rectangle")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX - currX);
            double lengthY = Math.abs(startY - currY);
            graphicsContext.strokeRect(Math.min(startX, currX), Math.min(startY, currY), lengthX, lengthY);
        } else if (Objects.equals(shapeComboBox.getValue(), "Line")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            graphicsContext.strokeLine(startX, startY, currX, currY);
        } else if (Objects.equals(shapeComboBox.getValue(), "Ellipse")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX - currX);
            double lengthY = Math.abs(startY - currY);
            graphicsContext.strokeOval(Math.min(startX, currX), Math.min(startY, currY), lengthX, lengthY);
        } else if (Objects.equals(shapeComboBox.getValue(), "Circle")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX - currX);
            double lengthY = Math.abs(startY - currY);
            double radius = Math.sqrt(lengthX * lengthX + lengthY * lengthY);
            graphicsContext.strokeOval(startX - radius, startY - radius, 2 * radius, 2 * radius);
        } else if (Objects.equals(shapeComboBox.getValue(), "Erase")) {
            double size = sizeComboBox.getValue();
            main.clearRect(mouseEvent.getX() - size / 2, mouseEvent.getY() - size / 2, size, size);
        }
    }

    VBox getFrame() {
        return frame;
    }

    void saveEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) mainFrame.getWidth(), (int) mainFrame.getHeight());
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                main.getCanvas().snapshot(params, writableImage);

                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("You need to select a file");
        }
    }

    void openEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            main.clearRect(0, 0, 1920, 900);
            back.clearRect(0, 0, 1920, 900);
            main.drawImage(image, 0, 0, 1920, 900);
        }
    }
}
