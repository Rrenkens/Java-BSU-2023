package by.lenson423.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas secondCanvas;

    @FXML
    public RadioButton brushButton;
    @FXML

    public RadioButton eraserButton;

    @FXML
    private RadioButton rectangleTool;

    @FXML
    private RadioButton circleTool;

    @FXML
    private RadioButton triangleTool;

    @FXML
    private CheckBox fillColorCheckBox;

    @FXML
    private Slider sizeSlider;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button clearCanvasButton;

    @FXML
    private Button saveImageButton;
    @FXML
    public Button openImageButton;
    FileChooser fileChooser = new FileChooser();

    private double prevMouseX, prevMouseY;

    private double lastX, lastY;
    private double brushWidth = 5;
    private Color selectedColor = Color.BLACK;

    public void initialize() {
        canvas.toFront();
        clearCanvas();

        ToggleGroup group = new ToggleGroup();
        rectangleTool.setToggleGroup(group);
        rectangleTool.getStyleClass().remove("radio-button");
        rectangleTool.getStyleClass().add("toggle-button");
        rectangleTool.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("rectangle.png")))));

        circleTool.setToggleGroup(group);
        circleTool.getStyleClass().remove("radio-button");
        circleTool.getStyleClass().add("toggle-button");
        circleTool.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("circle.png")))));

        triangleTool.setToggleGroup(group);
        triangleTool.getStyleClass().remove("radio-button");
        triangleTool.getStyleClass().add("toggle-button");
        triangleTool.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("triangle.png")))));

        brushButton.setToggleGroup(group);
        brushButton.getStyleClass().remove("radio-button");
        brushButton.getStyleClass().add("toggle-button");
        brushButton.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("brush.png")))));
        brushButton.setSelected(true);

        eraserButton.setToggleGroup(group);
        eraserButton.getStyleClass().remove("radio-button");
        eraserButton.getStyleClass().add("toggle-button");
        eraserButton.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("eraser.png")))));


        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> brushWidth = newValue.doubleValue());

        colorPicker.valueProperty().set(Color.BLACK);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> selectedColor = newValue);

        clearCanvasButton.setOnAction(event -> clearCanvas());

        saveImageButton.setOnAction(event -> {
            try {
                saveAsImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        openImageButton.setOnAction(event -> {
            try {
                openImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void openImage() throws IOException {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = SwingFXUtils.toFXImage(ImageIO.read(fileInputStream), null);
            clearCanvas();
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
            fileInputStream.close();
        }
    }

    private void saveAsImage() throws IOException {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Image image = canvas.snapshot(null, null);
            ImageIO.write(Objects.requireNonNull(SwingFXUtils.fromFXImage(image, null)), "png",
                    fileOutputStream);
            fileOutputStream.close();
        }
    }

    private void clearCanvas() {
        GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
        gcCanvas.setFill(Color.WHITE);
        gcCanvas.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        GraphicsContext gcSecondLayer = secondCanvas.getGraphicsContext2D();
        gcSecondLayer.setFill(Color.TRANSPARENT);
        gcSecondLayer.fillRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());
    }

    private void drawRect(MouseEvent e, GraphicsContext gc) {
        double width = Math.abs(prevMouseX - e.getX());
        double height = Math.abs(prevMouseY - e.getY());

        if (!fillColorCheckBox.isSelected()) {
            gc.strokeRect(Math.min(e.getX(), prevMouseX), Math.min(e.getY(), prevMouseY), width, height);
        } else {
            gc.fillRect(Math.min(e.getX(), prevMouseX), Math.min(e.getY(), prevMouseY), width, height);
        }
    }

    private void drawCircle(MouseEvent e, GraphicsContext gc) {
        double radius = Math.sqrt(Math.pow((prevMouseX - e.getX()), 2) + Math.pow((prevMouseY - e.getY()), 2));
        double centerX = (prevMouseX + e.getX()) / 2.0;
        double centerY = (prevMouseY + e.getY()) / 2.0;

        if (!fillColorCheckBox.isSelected()) {
            gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        } else {
            gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }


    private void drawTriangle(MouseEvent e, GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(prevMouseX, prevMouseY);
        gc.lineTo(e.getX(), e.getY());
        gc.lineTo(prevMouseX * 2 - e.getX(), e.getY());
        gc.closePath();
        if (fillColorCheckBox.isSelected()) {
            gc.fill();
        } else {
            gc.stroke();
        }
    }


    @FXML
    private void startDrawing(MouseEvent event) {
        prevMouseX = event.getX();
        prevMouseY = event.getY();
        lastX = event.getX();
        lastY = event.getY();
    }

    @FXML
    private void drawing(MouseEvent event) {
        double newX = event.getX();
        double newY = event.getY();
        if (brushButton.isSelected() || eraserButton.isSelected()) {
            drawLine(newX, newY);
        } else {
            GraphicsContext gc = secondCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());
            drawShape(event, gc, secondCanvas);
        }
        lastX = event.getX();
        lastY = event.getY();
    }

    private void drawShape(MouseEvent event, GraphicsContext gc, Canvas secondCanvas) {
        gc.setStroke(selectedColor);
        gc.setFill(selectedColor);
        gc.setLineWidth(brushWidth);
        if (rectangleTool.isSelected()) {
            drawRect(event, gc);
        } else if (circleTool.isSelected()) {
            drawCircle(event, gc);
        } else if (triangleTool.isSelected()) {
            drawTriangle(event, gc);
        }
        secondCanvas.toFront();
    }

    private void drawLine(double newX, double newY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(eraserButton.isSelected() ? Color.WHITE : selectedColor);
        gc.setLineWidth(brushWidth);
        gc.strokeLine(lastX, lastY, newX, newY);
    }

    @FXML
    private void endDrawing(MouseEvent event) {
        double newX = event.getX();
        double newY = event.getY();
        if (brushButton.isSelected() || eraserButton.isSelected()) {
            drawLine(newX, newY);
        } else {
            GraphicsContext gcSecondLayer = secondCanvas.getGraphicsContext2D();
            gcSecondLayer.clearRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());

            GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
            drawShape(event, gcCanvas, canvas);
        }
    }
}