package by.waitingsolong.paint;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;

public class PaintApplication extends Application {

    private GraphicsContext graphicsContext;
    private ColorPicker colorPicker;
    private Slider brushSize;
    private ToggleGroup shapeGroup;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 600);
        graphicsContext = canvas.getGraphicsContext2D();

        colorPicker = new ColorPicker();
        colorPicker.setMinHeight(30);
        brushSize = new Slider(0.1, 50, 10);

        RadioButton[] buttons = {
                new RadioButton("Brush"),
                new RadioButton("Circle"),
                new RadioButton("Rectangle"),
                new RadioButton("Line")
        };

        shapeGroup = new ToggleGroup();
        for (RadioButton button : buttons) {
            button.setToggleGroup(shapeGroup);
        }

        buttons[0].setSelected(true);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveToFile(canvas));

        Button openButton = new Button("Open");
        openButton.setOnAction(e -> openFromFile());

        VBox leftLayout = new VBox();
        leftLayout.getChildren().addAll(colorPicker, brushSize);
        leftLayout.getChildren().addAll(buttons);
        leftLayout.getChildren().addAll(saveButton, openButton);

        BorderPane layout = new BorderPane();
        layout.setLeft(leftLayout);
        layout.setCenter(canvas);

        double[] start = new double[2];

        canvas.setOnMouseDragged(e -> {
            double endX = e.getX();
            double endY = e.getY();

            graphicsContext.setStroke(colorPicker.getValue());
            graphicsContext.setLineWidth(brushSize.getValue());

            RadioButton selectedButton = (RadioButton) shapeGroup.getSelectedToggle();
            if (selectedButton.getText().equals("Brush")) {
                double radius = brushSize.getValue() / 2;
                double distance = Math.sqrt(Math.pow(endX - start[0], 2) + Math.pow(endY - start[1], 2));
                double angle = Math.atan2(endY - start[1], endX - start[0]);
                for (double i = 0; i < distance; i += radius * 2) {
                    double x = start[0] + Math.cos(angle) * i;
                    double y = start[1] + Math.sin(angle) * i;
                    graphicsContext.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
                }
                start[0] = endX;
                start[1] = endY;
            }
        });

        canvas.setOnMousePressed(e -> {
            start[0] = e.getX();
            start[1] = e.getY();
        });

        canvas.setOnMouseReleased(e -> {
            double endX = e.getX();
            double endY = e.getY();

            graphicsContext.setStroke(colorPicker.getValue());
            graphicsContext.setLineWidth(brushSize.getValue());

            RadioButton selectedButton = (RadioButton) shapeGroup.getSelectedToggle();
            switch (selectedButton.getText()) {
                case "Circle" -> graphicsContext.strokeOval(start[0], start[1], endX - start[0], endY - start[1]);
                case "Rectangle" -> graphicsContext.strokeRect(start[0], start[1], endX - start[0], endY - start[1]);
                case "Line" -> graphicsContext.strokeLine(start[0], start[1], endX, endY);
            }
        });

        canvas.setStyle("-fx-background-color: #ffffff;");
        leftLayout.setStyle("-fx-background-color: #f4d6ff;");

        primaryStage.setScene(new Scene(layout));
        primaryStage.setTitle(".");
        primaryStage.show();
    }


    private void saveToFile(Canvas canvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void openFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                graphicsContext.drawImage(image, 0, 0);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}