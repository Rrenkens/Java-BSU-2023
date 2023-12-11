package by.Lenson423.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Canvas canvas;

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

    private GraphicsContext gc;
    private double prevMouseX, prevMouseY;
    private boolean isDrawing = false;
    private double brushWidth = 5;
    private Color selectedColor = Color.BLACK;
    private WritableImage snapshot;

    private Canvas secondCanvas = new Canvas();

    private boolean isLastIteration = false;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        canvas.setHeight(420);
        canvas.setWidth(570);
        canvas.setOnMousePressed(this::startDraw);
        canvas.setOnMouseDragged(this::drawing);
        canvas.setOnMouseReleased(e -> {
            isDrawing = false;
            isLastIteration = true;
            drawing(e);
        });
        setCanvasBackground(canvas);

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

        clearCanvasButton.setOnAction(event -> {
            gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            setCanvasBackground(canvas);
        });

        saveImageButton.setOnAction(event -> {
            // You can implement the image saving logic here
        });
    }

    private void snapshot() {
        snapshot = canvas.snapshot(null, null);
    }

    private void restoreSnapshot() {
        gc.drawImage(snapshot, 0, 0);
    }

    private void swapCanvases() {
        Canvas tmp = canvas;
        canvas = secondCanvas;
        secondCanvas = tmp;
    }

    private void setCanvasBackground(Canvas canvas) {
        GraphicsContext gc2 = canvas.getGraphicsContext2D();
        gc2.setFill(Color.WHITE);
        gc2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc2.setFill(selectedColor);
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

    private void startDraw(MouseEvent e) {
        snapshot();
        swapCanvases();
        isDrawing = true;
        prevMouseX = e.getX();
        prevMouseY = e.getY();
        gc.beginPath();
        gc.setLineWidth(brushWidth);
        gc.setStroke(selectedColor);
        gc.setFill(selectedColor);
    }

    private void drawing(MouseEvent e) {
        if (!isDrawing && !isLastIteration) {
            return;
        } else if (isLastIteration) {
            swapCanvases();
            isLastIteration = false;
        } else {
            restoreSnapshot();
        }

        if (brushButton.isSelected() || eraserButton.isSelected()) {
            gc.setStroke(eraserButton.isSelected() ? Color.WHITE : selectedColor);
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        } else if (rectangleTool.isSelected()) {
            drawRect(e, gc);
        } else if (circleTool.isSelected()) {
            drawCircle(e, gc);
        } else if (triangleTool.isSelected()) {
            drawTriangle(e, gc);
        }
    }
}