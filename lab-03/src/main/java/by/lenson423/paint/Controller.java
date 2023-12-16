package by.lenson423.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;

public class Controller {
    @FXML
    private ComboBox<RadioButtonWithBiFunction<?, ?>> comboBox;

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas secondCanvas;

    @FXML
    private RadioButtonWithBiFunction<Double, Double> brushButton;

    @FXML
    private RadioButtonWithBiFunction<Double, Double> eraserButton;

    @FXML
    private RadioButtonWithBiFunction<MouseEvent, GraphicsContext> rectangleTool;

    @FXML
    private RadioButtonWithBiFunction<MouseEvent, GraphicsContext> circleTool;

    @FXML
    private RadioButtonWithBiFunction<MouseEvent, GraphicsContext> triangleTool;

    @FXML
    private RadioButtonWithBiFunction<MouseEvent, GraphicsContext> heartTool;

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
    private Button openImageButton;

    private final ArrayList<RadioButtonWithBiFunction> radioButtons = new ArrayList<>();
    private final FileChooser fileChooser = new FileChooser();
    private double prevMouseX, prevMouseY;
    private double lastX, lastY;
    private double brushWidth = 5;
    private Color selectedColor = Color.BLACK;

    public void initialize() {
        canvas.toFront();
        clearCanvas();

        processRadioButtons();

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

    private void processRadioButtons() {
        ToggleGroup group = new ToggleGroup();

        addRadioButtonToGroup(group, eraserButton, "eraser.png", this::drawLineOrErase);
        addRadioButtonToGroup(group, brushButton, "brush.png", this::drawLineOrErase);
        addRadioButtonToGroup(group, triangleTool, "triangle.png", this::drawTriangle);
        addRadioButtonToGroup(group, circleTool, "circle.png", this::drawCircle);
        addRadioButtonToGroup(group, rectangleTool, "rectangle.png", this::drawRect);
        addRadioButtonToGroup(group, heartTool, "heart.png", this::drawHeart);

        heartTool.setSelected(true);
    }

    private <U, V> void addRadioButtonToGroup(ToggleGroup group, RadioButtonWithBiFunction<U, V> button, String path,
                                              BiFunction<U, V, Void> func) {
        button.setToggleGroup(group);
        button.getStyleClass().remove("radio-button");
        button.getStyleClass().add("toggle-button");
        button.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource(path)))));
        button.setFunction(func);
        radioButtons.add(button);
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

    private Void drawRect(MouseEvent e, GraphicsContext gc) {
        double width = Math.abs(prevMouseX - e.getX());
        double height = Math.abs(prevMouseY - e.getY());

        if (!fillColorCheckBox.isSelected()) {
            gc.strokeRect(Math.min(e.getX(), prevMouseX), Math.min(e.getY(), prevMouseY), width, height);
        } else {
            gc.fillRect(Math.min(e.getX(), prevMouseX), Math.min(e.getY(), prevMouseY), width, height);
        }
        return null;
    }

    private Void drawCircle(MouseEvent e, GraphicsContext gc) {
        double radius = Math.sqrt(Math.pow((prevMouseX - e.getX()), 2) + Math.pow((prevMouseY - e.getY()), 2));
        double centerX = (prevMouseX + e.getX()) / 2.0;
        double centerY = (prevMouseY + e.getY()) / 2.0;

        if (!fillColorCheckBox.isSelected()) {
            gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        } else {
            gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
        return null;
    }


    private Void drawTriangle(MouseEvent e, GraphicsContext gc) {
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
        return null;
    }

    private Void drawHeart(MouseEvent e, GraphicsContext gc) {
        double xMax = Math.min(e.getX(), prevMouseX);
        double xMin = Math.max(e.getX(), prevMouseX);
        double yMax = Math.min(e.getY(), prevMouseY);
        double yMin = Math.max(e.getY(), prevMouseY);

        double deltaX = xMax - xMin;
        double deltaY = yMax - yMin;

        gc.beginPath();
        gc.moveTo(xMin + 0.5 * deltaX, yMin);
        gc.bezierCurveTo(xMin, yMin + 0.65 * deltaY, xMin + 0.25 * deltaX, yMax,
                xMin + 0.5 * deltaX, yMin + 0.75 * deltaY);
        gc.bezierCurveTo(xMin + 0.75 * deltaX, yMax, xMax, yMin + 0.65 * deltaY, xMin + 0.5 * deltaX, yMin);
        gc.closePath() ;

        if (fillColorCheckBox.isSelected()) {
            gc.fill();
        } else {
            gc.stroke();
        }
        return null;
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
            drawLineOrErase(newX, newY);
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
        for (var button : radioButtons) {
            if (button.isSelected()) {
                button.getFunction().apply(event, gc);
            }
        }
        secondCanvas.toFront();
    }

    private Void drawLineOrErase(double newX, double newY) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(eraserButton.isSelected() ? Color.WHITE : selectedColor);
        gc.setLineWidth(brushWidth);
        gc.strokeLine(lastX, lastY, newX, newY);
        return null;
    }

    @FXML
    private void endDrawing(MouseEvent event) {
        double newX = event.getX();
        double newY = event.getY();
        if (brushButton.isSelected() || eraserButton.isSelected()) {
            drawLineOrErase(newX, newY);
        } else {
            GraphicsContext gcSecondLayer = secondCanvas.getGraphicsContext2D();
            gcSecondLayer.clearRect(0, 0, secondCanvas.getWidth(), secondCanvas.getHeight());

            GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
            drawShape(event, gcCanvas, canvas);
        }
    }

    @FXML
    private void comboBoxChanged(ActionEvent ignored) {
        RadioButtonWithBiFunction<?, ?> selectedRadioButton = comboBox.getValue();
        selectedRadioButton.setSelected(true);
    }
}
