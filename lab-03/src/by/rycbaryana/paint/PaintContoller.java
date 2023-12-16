package by.rycbaryana.paint;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.FileChooser;

import java.io.*;

public class PaintContoller {
    public Canvas canvas;
    public Canvas infoCanvas;
    public Slider penSize;
    public ColorPicker lineColor;
    public ColorPicker fillColor;
    public ToggleGroup tools;
    public ToggleButton eraserButton;
    public ToggleButton penButton;
    public ToggleButton rectButton;
    public Button clearButton;
    public CheckBox doFill;
    public ToggleButton circleButton;

    GraphicsContext context;

    GraphicsContext infoContext;

    double prevX, prevY;

    final FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize() {
        canvas.toFront();
        context = canvas.getGraphicsContext2D();
        infoContext = infoCanvas.getGraphicsContext2D();
        clearCanvas(canvas, true);
        clearCanvas(infoCanvas, false);

        lineColor.setValue(Color.BLACK);
        fillColor.setValue(Color.BLACK);
        penButton.setSelected(true);

        clearButton.setOnAction(e -> clearCanvas(canvas, true));

        canvas.setOnMouseMoved(this::showCursor);

        penSize.valueProperty().addListener(e -> {
            context.setLineWidth(penSize.getValue());
            infoContext.setLineWidth(penSize.getValue());
        });
        lineColor.valueProperty().addListener(e -> {
            context.setStroke(lineColor.getValue());
            infoContext.setStroke(lineColor.getValue());
        });
        fillColor.valueProperty().addListener(e ->
        {
            context.setFill(fillColor.getValue());
            infoContext.setFill(fillColor.getValue());
        });
    }

    @FXML
    protected void onOpen() {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Choose file to load", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Image image = SwingFXUtils.toFXImage(ImageIO.read(fileInputStream), null);
            clearCanvas(canvas, false);
            context.drawImage(image, 0, 0);
        } catch (IOException e) {
            System.out.println("Bad file");
        }
    }

    @FXML
    protected void onSave() {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Choose file to save", "*.png", "*.jpg"));
        File file = fileChooser.showSaveDialog(null);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            Image image = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fileOutputStream);
        } catch (IOException e) {
            System.out.println("Bad file");
        }
    }

    @FXML
    protected void onDrag(MouseEvent e) {
        context.save();
        showCursor(e);
        if (penButton.isSelected()) {
            draw(e);
        } else if (eraserButton.isSelected()) {
            erase(e);
        } else if (rectButton.isSelected()) {
            drawRect(e, infoContext);
        } else if (circleButton.isSelected()) {
            drawCircle(e, infoContext);
        }
        context.restore();
    }

    @FXML
    protected void onPress(MouseEvent e) {
        if (penButton.isSelected()) {
            context.beginPath();
            draw(e);
        } else if (eraserButton.isSelected()) {
            erase(e);
        } else {
            prevX = e.getX();
            prevY = e.getY();
        }
    }

    @FXML
    protected void onRelease(MouseEvent e) {
        if (rectButton.isSelected()) {
            drawRect(e, context);
        } else if (circleButton.isSelected()) {
            drawCircle(e, context);
        }
    }

    void showCursor(MouseEvent e) {
        infoContext.save();
        infoContext.setLineWidth(1);
        clearCanvas(infoCanvas, false);
        var w = context.getLineWidth();
        if (penButton.isSelected()) {
            infoContext.strokeOval(e.getX() - w / 2, e.getY() - w / 2, w, w);
        }
        if (eraserButton.isSelected()) {
            infoContext.strokeRect(e.getX() - w / 2, e.getY() - w / 2, w, w);
        }
        infoContext.restore();
    }

    void clearCanvas(Canvas canvas, boolean transparent) {
        this.context.save();
        infoContext.save();
        var context = canvas.getGraphicsContext2D();
        if (transparent) {
            context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        } else {
            context.setFill(Color.WHITE);
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
        infoContext.restore();
        this.context.restore();
    }

    void erase(MouseEvent e) {
        var w = context.getLineWidth();
        context.clearRect(e.getX() - w / 2, e.getY() - w / 2, w, w);
    }

    void draw(MouseEvent e) {
        context.save();
        context.setLineCap(StrokeLineCap.ROUND);
        context.setLineJoin(StrokeLineJoin.ROUND);
        context.lineTo(e.getX(), e.getY());
        context.stroke();
        context.restore();
    }

    void drawRect(MouseEvent e, GraphicsContext context) {
        clearCanvas(infoCanvas, false);
        double x = e.getX();
        double y = e.getY();
        double w = Math.abs(x - prevX);
        double h = Math.abs(y - prevY);
        if (doFill.isSelected()) {
            context.fillRect(Math.min(prevX, x), Math.min(prevY, y), w, h);
        } else {
            context.strokeRect(Math.min(prevX, x), Math.min(prevY, y), w, h);
        }
    }

    void drawCircle(MouseEvent e, GraphicsContext context) {
        clearCanvas(infoCanvas, false);
        double w = Math.abs(e.getX() - prevX);
        if (doFill.isSelected()) {
            context.fillOval(prevX - w / 2, prevY - w / 2, w, w);
        } else {
            context.strokeOval(prevX - w / 2, prevY - w / 2, w, w);
        }
    }
}