package by.lamposhka.paint;

import com.sun.javafx.sg.prism.NGRectangle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PaintController {

    private enum BrushMode {
        PEN, CIRCLE, RECTANGLE, ERASER
    }

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private ComboBox<BrushMode> brushChoiceBox;
    private GraphicsContext graphicsContext;
    private double prevX, prevY;
    WritableImage snapshot;

    private final int pixelScaleFactor = 2;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        brushSize.setText("20");
        brushChoiceBox.setItems(FXCollections.observableArrayList(BrushMode.values()));
        brushChoiceBox.setValue(BrushMode.PEN);
        colorPicker.setValue(Color.BLACK);
        prevX = 0;
        prevY = 0;
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.WHITE);
        snapshot = canvas.snapshot(parameters, null);
    }

    public void onOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Load the selected image file
            Image image = new Image(selectedFile.toURI().toString());

            // Clear the canvas
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Draw the image on the canvas
            canvas.getGraphicsContext2D().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    public void canvasMousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
        graphicsContext.beginPath();
        WritableImage writableImage = new WritableImage((int) Math.rint(pixelScaleFactor * canvas.getWidth()), (int) Math.rint(pixelScaleFactor * canvas.getHeight()));
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setTransform(Transform.scale(pixelScaleFactor, pixelScaleFactor));
        parameters.setFill(Color.WHITE);
        snapshot = canvas.snapshot(parameters, writableImage);
    }

    public void canvasMouseDragged(MouseEvent mouseEvent) {
        double size = Double.parseDouble(brushSize.getText());
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        graphicsContext.setLineWidth(size);
        graphicsContext.setLineCap(StrokeLineCap.ROUND);
        graphicsContext.setLineJoin(StrokeLineJoin.ROUND);

        switch (brushChoiceBox.getValue()) {
            case ERASER:
                graphicsContext.setStroke(Color.WHITE);
                graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
                graphicsContext.stroke();
                break;
            case PEN:
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
                graphicsContext.stroke();
                break;
            case RECTANGLE:
                graphicsContext.drawImage(snapshot, 0, 0, canvas.getHeight(), canvas.getWidth());
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.strokeRect(Math.min(prevX, x), Math.min(prevY, y), Math.abs(x - prevX), Math.abs(y - prevY));
                break;
            case CIRCLE:
                graphicsContext.drawImage(snapshot, 0, 0, canvas.getHeight(), canvas.getWidth());
                graphicsContext.setStroke(colorPicker.getValue());
                double radius = Math.sqrt((x - prevX) * (x - prevX) + (y - prevY) * (y - prevY));
                graphicsContext.strokeOval(prevX - radius, prevY - radius, 2 * radius, 2 * radius);
                break;
        }

    }

    public void canvasMouseReleased(MouseDragEvent mouseEvent) {
        WritableImage writableImage = new WritableImage((int) Math.rint(pixelScaleFactor * canvas.getWidth()), (int) Math.rint(pixelScaleFactor * canvas.getHeight()));
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setTransform(Transform.scale(pixelScaleFactor, pixelScaleFactor));
        parameters.setFill(Color.WHITE);
        snapshot = canvas.snapshot(parameters, writableImage);
    }

    public void onExit() {
        Platform.exit();
    }

    public void onSave(ActionEvent actionEvent) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.WHITE);
        WritableImage writableImage = canvas.snapshot(parameters, null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Canvas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
