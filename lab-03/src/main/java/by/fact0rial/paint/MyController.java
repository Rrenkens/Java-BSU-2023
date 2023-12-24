package by.fact0rial.paint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MyController {
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker lineColor;
    @FXML
    private ColorPicker fillColor;
    private GraphicsContext g = null;
    @FXML
    private Slider thickness;
    Mode m = Mode.Draw;
    double startX = 0;
    double startY = 0;
    public void initialize() {
        g = canvas.getGraphicsContext2D();
        lineColor.setValue(Color.BLACK);
        fillColor.setValue(Color.AQUA);
        canvas.setOnMousePressed(this::mousePress);
        canvas.setOnMouseDragged(this::mouseDrag);
        canvas.setOnMouseReleased(this::mouseDragExited);
    }

    public void onDrawButtonPress() {
        m = Mode.Draw;
    }
    public void onCircleButtonPress() {
        m = Mode.Circle;
    }
    public void onRectangleButtonPress() {
        m = Mode.Rectangle;
    }
    public void onLineButtonPress() {
        m = Mode.Line;
    }
    private void mousePress(MouseEvent e) {
        g.setLineWidth(thickness.getValue());
        g.setStroke(lineColor.getValue());
        g.setFill(fillColor.getValue());
        switch (m) {
            case Draw -> {
                g.beginPath();
                g.moveTo(e.getX(), e.getY());
                g.stroke();
            }
            case Circle, Rectangle, Line, FilledRectangle, FilledCircle -> {
                startX = e.getX();
                startY = e.getY();
            }
        }
    }
    private void mouseDrag(MouseEvent e) {
        switch (m) {
            case Draw -> {
                g.lineTo(e.getX(), e.getY());
                g.stroke();
            }
        }
    }
    private void mouseDragExited(MouseEvent e) {
        double x1 = Math.min(startX, e.getX());
        double x2 = Math.abs(startX - e.getX());
        double y1 = Math.min(startY, e.getY());
        double y2 = Math.abs(startY - e.getY());
        switch (m) {
            case Circle -> {
                g.strokeOval(x1, y1, x2, y2);
            }
            case Rectangle -> {
                g.strokeRect(x1, y1, x2, y2);
            }
            case FilledCircle -> {
                g.fillOval(x1,y1,x2,y2);
                g.strokeOval(x1, y1, x2, y2);
            }
            case FilledRectangle -> {
                g.fillRect(x1, y1, x2, y2);
                g.strokeRect(x1, y1, x2, y2);
            }
            case Line -> {
                g.strokeLine(startX, startY, e.getX(), e.getY());
            }
        }
    }

    public void onFilledCircleButtonPress() {
        m = Mode.FilledCircle;
    }

    public void onFilledRectangleButtonPress() {
        m = Mode.FilledRectangle;
    }

    public void onSaveButtonPress(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        File file = chooser.showSaveDialog(stage);
        WritableImage im = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        canvas.snapshot(null, im);
        BufferedImage buff = SwingFXUtils.fromFXImage(im, null);
        try {
            ImageIO.write(buff, "png", file);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении рисунка: " + e.getMessage());
        }
    }

    public void onOpenButtonPress(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        File file = chooser.showOpenDialog(stage);
        BufferedImage buff = null;
        try {
            buff = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("Ошибка при открытии рисунка: " + e.getMessage());
        }
        Image im = SwingFXUtils.toFXImage(buff, null);
        g.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        g.drawImage(im, 0, 0);
    }
}