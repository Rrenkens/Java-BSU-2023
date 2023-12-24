package by.nrydo.paint;

import javafx.fxml.FXML;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ColorPicker;
import javafx.collections.FXCollections;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Slider;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Slider brushSize;
    @FXML
    private ComboBox<DrawingMode> modeChooser;
    @FXML
    private ColorPicker firstColorPicker;
    @FXML
    private ColorPicker secondColorPicker;
    private GraphicsContext graphicsContext;
    private WritableImage image;
    private double fixedX;
    private double fixedY;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        modeChooser.setItems(FXCollections.observableArrayList(DrawingMode.values()));
        modeChooser.setValue(DrawingMode.PEN);
        firstColorPicker.setValue(Color.BLACK);
        secondColorPicker.setValue(Color.WHITE);
        brushSize.setValue(5);
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);
        fixedX = 0;
        fixedY = 0;
    }

    public void mousePressed(MouseEvent event) {
        fixedX = event.getX();
        fixedY = event.getY();
        graphicsContext.beginPath();
        image = canvas.snapshot(null, null);
    }

    public void mouseReleased(MouseEvent event) {
        image = canvas.snapshot(null, null);
    }

    public void mouseDragged(MouseEvent event) {
        double size = brushSize.getValue();
        double curX = event.getX();
        double curY = event.getY();

        graphicsContext.setLineWidth(size);
        switch (event.getButton()) {
            case MouseButton.PRIMARY:
                graphicsContext.setStroke(firstColorPicker.getValue());
                break;
            case MouseButton.SECONDARY:
                graphicsContext.setStroke(secondColorPicker.getValue());
                break;
            default:
                return;
        }

        switch (modeChooser.getValue()) {
            case PEN:
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                break;
            case LINE:
                graphicsContext.drawImage(image, 0, 0, canvas.getHeight(), canvas.getWidth());
                graphicsContext.strokeLine(fixedX, fixedY, curX, curY);
                break;
            case RECTANGLE:
                graphicsContext.drawImage(image, 0, 0, canvas.getHeight(), canvas.getWidth());
                graphicsContext.strokeRect(Math.min(fixedX, curX), Math.min(fixedY, curY), Math.abs(curX - fixedX), Math.abs(curY - fixedY));
                break;
            case ELLIPSE:
                graphicsContext.drawImage(image, 0, 0, canvas.getHeight(), canvas.getWidth());
                graphicsContext.strokeOval(Math.min(fixedX, curX), Math.min(fixedY, curY), Math.abs(curX - fixedX), Math.abs(curY - fixedY));
                break;
            case ERASER:
                graphicsContext.setStroke(secondColorPicker.getValue());
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                break;
        }
    }

    public void swapColors() {
        var buffer = firstColorPicker.getValue();
        firstColorPicker.setValue(secondColorPicker.getValue());
        secondColorPicker.setValue(buffer);
    }

    public void clearImage() {
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void saveImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                WritableImage temp = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, temp);
                ImageIO.write(SwingFXUtils.fromFXImage(temp, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            clearImage();
            canvas.getGraphicsContext2D().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }
}