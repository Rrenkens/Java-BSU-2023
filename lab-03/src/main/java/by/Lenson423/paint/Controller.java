package by.Lenson423.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
            // You can implement the image saving logic here
        });
    }

    private void clearCanvas(){
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
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(selectedColor);
            gc.setLineWidth(brushWidth);
            gc.strokeLine(lastX, lastY, newX, newY);
        } else {
            GraphicsContext gc = secondCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());
            gc.setStroke(selectedColor);
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
        lastX = event.getX();
        lastY = event.getY();
    }

    @FXML
    private void endDrawing(MouseEvent event) {
        double newX = event.getX();
        double newY = event.getY();
        if (brushButton.isSelected() || eraserButton.isSelected()) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(selectedColor);
            gc.setLineWidth(brushWidth);
            gc.strokeLine(lastX, lastY, newX, newY);
        } else {
            GraphicsContext gcSecondLayer = secondCanvas.getGraphicsContext2D();
            gcSecondLayer.clearRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());

            GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
            gcCanvas.setStroke(selectedColor);
            gcCanvas.setLineWidth(brushWidth);
            if (rectangleTool.isSelected()) {
                drawRect(event, gcCanvas);
            } else if (circleTool.isSelected()) {
                drawCircle(event, gcCanvas);
            } else if (triangleTool.isSelected()) {
                drawTriangle(event, gcCanvas);
            }
            canvas.toFront();
        }
    }

    @FXML
    private void drawPoint(MouseEvent event) {
        double newX = event.getX();
        double newY = event.getY();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(selectedColor);
        gc.setLineWidth(brushWidth);
        gc.strokeLine(newX, newY, newX, newY);
    }
}