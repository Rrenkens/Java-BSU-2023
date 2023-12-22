package by.aadeglmmy.paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SimplePaintApp extends Application {

  Canvas canvas = new Canvas(800, 800);
  DrawingData drawing = new DrawingData();
  private GraphicsContext graphicsContext;
  private ColorPicker colorPicker;
  private ComboBox<Integer> brushSizeComboBox;
  private double startX, startY;
  private boolean ovalMode = false;
  private boolean rectangleMode = false;
  private boolean brushMode = true;
  private double previousX, previousY;

  public SimplePaintApp() {
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Rectangle canvasBorder = new Rectangle(800, 800);
    canvasBorder.setStroke(Color.BLACK);
    canvasBorder.setFill(Color.TRANSPARENT);
    graphicsContext = canvas.getGraphicsContext2D();
    graphicsContext.setStroke(Color.BLACK);
    graphicsContext.setLineWidth(1.0);

    colorPicker = new ColorPicker();
    colorPicker.setValue(Color.BLACK);
    colorPicker.setOnAction(e -> graphicsContext.setStroke(colorPicker.getValue()));

    brushSizeComboBox = new ComboBox<>();
    brushSizeComboBox.getItems().addAll(1, 3, 5, 8);
    brushSizeComboBox.setValue(1);
    brushSizeComboBox.setOnAction(e -> graphicsContext.setLineWidth(brushSizeComboBox.getValue()));

    Button brushButton = new Button("Brush");
    Button ovalButton = new Button("Oval");
    Button rectangleButton = new Button("Rectangle");

    brushButton.setOnAction(e -> {
      ovalMode = false;
      rectangleMode = false;
      brushMode = true;
    });

    ovalButton.setOnAction(e -> {
      ovalMode = true;
      rectangleMode = false;
      brushMode = false;
    });

    rectangleButton.setOnAction(e -> {
      ovalMode = false;
      rectangleMode = true;
      brushMode = false;
    });

    canvas.setOnMousePressed(e -> {
      startX = e.getX();
      startY = e.getY();
      previousX = startX;
      previousY = startY;
    });

    canvas.setOnMouseDragged(e -> {
      if (brushMode) {
        double x = e.getX();
        double y = e.getY();
        new LineData(previousX, previousY, x, y, colorPicker.getValue(),
            brushSizeComboBox.getValue(), drawing);
        graphicsContext.strokeLine(previousX, previousY, x, y);
        previousX = x;
        previousY = y;
      }
    });

    canvas.setOnMouseReleased(e -> {
      double endX = e.getX();
      double endY = e.getY();

      double x = Math.min(startX, endX);
      double y = Math.min(startY, endY);
      double width = Math.abs(endX - startX);
      double height = Math.abs(endY - startY);

      if (e.isShiftDown()) {
        double minLength = Math.min(width, height);
        width = minLength;
        height = minLength;
      }

      if (ovalMode) {
        new OvalData(x, y, width, height, colorPicker.getValue(), brushSizeComboBox.getValue(),
            drawing);
        graphicsContext.strokeOval(x, y, width, height);
      } else if (rectangleMode) {
        new RectangleData(x, y, width, height, colorPicker.getValue(), brushSizeComboBox.getValue(),
            drawing);
        graphicsContext.strokeRect(x, y, width, height);
      }
    });

    Button saveButton = new Button("Save");
    Button openButton = new Button("Open");

    saveButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save the drawing");
      File file = fileChooser.showSaveDialog(primaryStage);
      if (file != null) {
        saveDrawing(file.getAbsolutePath());
      }
    });

    openButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open the drawing");
      File file = fileChooser.showOpenDialog(primaryStage);
      if (file != null) {
        openDrawing(file.getAbsolutePath());
      }
    });

    HBox buttonsBox = new HBox(brushButton, ovalButton, rectangleButton, saveButton, openButton);
    buttonsBox.setSpacing(10);
    buttonsBox.setPadding(new Insets(10));

    HBox colorAndSizeBox = new HBox(colorPicker, brushSizeComboBox);
    colorAndSizeBox.setSpacing(10);
    colorAndSizeBox.setPadding(new Insets(10));

    Pane canvasPane = new StackPane();
    canvasPane.getChildren().addAll(canvasBorder, canvas);

    BorderPane root = new BorderPane();
    root.setTop(colorAndSizeBox);
    root.setCenter(canvasPane);
    root.setLeft(buttonsBox);

    Scene scene = new Scene(root, 1600, 900);

    primaryStage.setTitle("Simple Paint");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void saveDrawing(String filename) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
      outputStream.writeObject(drawing);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void openDrawing(String filename) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
      DrawingData drawingData = (DrawingData) inputStream.readObject();

      graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      drawing = drawingData;

      for (LineData line : drawingData.getLines()) {
        graphicsContext.setStroke(line.getColor());
        graphicsContext.setLineWidth(line.getWidth());
        graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(),
            line.getEndY());
      }

      for (OvalData oval : drawingData.getOvals()) {
        graphicsContext.setStroke(oval.getColor());
        graphicsContext.setLineWidth(oval.getBrushWidth());
        graphicsContext.strokeOval(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
      }

      for (RectangleData rectangle : drawingData.getRectangles()) {
        graphicsContext.setStroke(rectangle.getColor());
        graphicsContext.setLineWidth(rectangle.getBrushWidth());
        graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
            rectangle.getHeight());
      }

      graphicsContext.setStroke(colorPicker.getValue());
      graphicsContext.setLineWidth(brushSizeComboBox.getValue());
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
