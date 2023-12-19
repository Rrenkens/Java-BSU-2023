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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    private ColorPicker penColorPicker_, fillColorPicker_;
    private Button clearButton_, saveButton_, openButton_, transparentButton_;
    private ComboBox<String> shapeComboBox_;
    private ComboBox<Double> sizeComboBox_;
    private Canvas mainFrame_, backFrame_;
    private GraphicsContext main_, back_;
    private VBox frame_;
    private double startX_;
    private double startY_;

    public void Initialization() {
        mainFrame_ = new Canvas(1920, 900);
        backFrame_ = new Canvas(1920, 900);

        main_ = mainFrame_.getGraphicsContext2D();
        back_ = backFrame_.getGraphicsContext2D();

        penColorPicker_ = new ColorPicker(Color.BLACK);
        penColorPicker_.setPrefSize(150, 50);
        fillColorPicker_ = new ColorPicker(Color.TRANSPARENT);
        fillColorPicker_.setPrefSize(150, 50);

        clearButton_ = new Button("Clear");
        clearButton_.setPrefSize(150, 50);
        clearButton_.setOnAction(event -> {
            main_.clearRect(0, 0, 1920, 900);
            back_.clearRect(0, 0, 1920, 900);
        });

        transparentButton_ = new Button("Set transparent");
        transparentButton_.setPrefSize(150, 50);
        transparentButton_.setOnAction(actionEvent -> fillColorPicker_.setValue(Color.TRANSPARENT));

        sizeComboBox_ = new ComboBox<>();
        sizeComboBox_.setPrefSize(150, 50);
        sizeComboBox_.getItems().addAll(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
                10.0, 12.0, 14.0, 16.0, 18.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0, 50.0);
        sizeComboBox_.setValue(2.0);

        shapeComboBox_ = new ComboBox<>();
        shapeComboBox_.setPrefSize(150, 50);
        shapeComboBox_.getItems().addAll("Freehand", "Rectangle", "Ellipse", "Circle", "Erase");
        shapeComboBox_.setValue("Freehand");

        saveButton_ = new Button("Save");
        saveButton_.setPrefSize(150, 50);
        saveButton_.setOnAction(event -> saveEvent());

        openButton_ = new Button("Open");
        openButton_.setPrefSize(150, 50);
        openButton_.setOnAction(event -> openEvent());

        HBox toolBar_ = new HBox(saveButton_, openButton_, penColorPicker_, fillColorPicker_, transparentButton_, sizeComboBox_, shapeComboBox_, clearButton_);
        StackPane stackPane = new StackPane(backFrame_, mainFrame_);
        frame_ = new VBox(toolBar_, stackPane);

        mainFrame_.setOnMousePressed(mouseEvent -> {
            startX_ = mouseEvent.getX();
            startY_ = mouseEvent.getY();

            main_.beginPath();
            main_.setStroke(penColorPicker_.getValue());
            main_.setLineWidth(sizeComboBox_.getValue());
            main_.setFill(fillColorPicker_.getValue());

            back_.beginPath();
            back_.setStroke(penColorPicker_.getValue());
            back_.setLineWidth(sizeComboBox_.getValue());
            back_.setFill(fillColorPicker_.getValue());
        });

        mainFrame_.setOnMouseDragged(mouseEvent -> {
            back_.clearRect(0, 0, 1920, 900);
            mainFrame_.toBack();
            paintEvent(mouseEvent, back_);
        });

        mainFrame_.setOnMouseReleased(mouseEvent -> {
            mainFrame_.toFront();
            paintEvent(mouseEvent, main_);
        });
    }

    void paintEvent(MouseEvent mouseEvent, GraphicsContext graphicsContext) {
        if (Objects.equals(shapeComboBox_.getValue(), "Freehand")) {
            main_.lineTo(mouseEvent.getX(), mouseEvent.getY());
            main_.stroke();
        } else if (Objects.equals(shapeComboBox_.getValue(), "Rectangle")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX_ - currX);
            double lengthY = Math.abs(startY_ - currY);
            graphicsContext.strokeRect(Math.min(startX_, currX), Math.min(startY_, currY), lengthX, lengthY);
            graphicsContext.fillRect(Math.min(startX_, currX), Math.min(startY_, currY), lengthX, lengthY);
        } else if (Objects.equals(shapeComboBox_.getValue(), "Ellipse")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX_ - currX);
            double lengthY = Math.abs(startY_ - currY);
            graphicsContext.strokeOval(Math.min(startX_, currX), Math.min(startY_, currY), lengthX, lengthY);
            graphicsContext.fillOval(Math.min(startX_, currX), Math.min(startY_, currY), lengthX, lengthY);
        } else if (Objects.equals(shapeComboBox_.getValue(), "Circle")) {
            double currX = mouseEvent.getX();
            double currY = mouseEvent.getY();
            double lengthX = Math.abs(startX_ - currX);
            double lengthY = Math.abs(startY_ - currY);
            double radius = Math.sqrt(lengthX * lengthX + lengthY * lengthY);
            graphicsContext.strokeOval(startX_ - radius, startY_ - radius, 2 * radius, 2 * radius);
            graphicsContext.fillOval(startX_ - radius, startY_ - radius, 2 * radius, 2 * radius);
        } else if (Objects.equals(shapeComboBox_.getValue(), "Erase")) {
            double size = sizeComboBox_.getValue();
            main_.clearRect(mouseEvent.getX() - size / 2, mouseEvent.getY() - size / 2, size, size);
        }
    }

    VBox getFrame_() {
        return frame_;
    }

    void saveEvent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) mainFrame_.getWidth(), (int) mainFrame_.getHeight());
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                main_.getCanvas().snapshot(params, writableImage);

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
            main_.clearRect(0, 0, 1920, 900);
            back_.clearRect(0, 0, 1920, 900);
            main_.drawImage(image, 0, 0, 1920, 900);
        }
    }
}
