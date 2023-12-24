package by.BelArtem.paint;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class Paint extends Application {

    private HBox root;
    private VBox vBox;
    private Label comboboxLabel;
    private Label sliderLabel;
    private Label colorLabel;
    private ComboBox<String> figures;
    private Slider sizeSlider;
    private ColorPicker colorPicker;
    private Button clearButton;
    private Button saveButton;
    private Button loadButton;
    private Pane drawingPane;
    private FileChooser fileChooser;
    private String curFigure;
    private int curSize;
    private Color curColor;
    private Point point;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.setVboxLayout();

        root = new HBox();
        Scene scene = new Scene(root, 1500, 900);
        root.getChildren().add(vBox);

        this.setDrawingPane();
        HBox.setMargin(drawingPane, new Insets(0,0,0,10));
        root.getChildren().add(drawingPane);

        this.setToolBarActions();
        this.setDrawingActions();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        point = new Point();

        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.setResizable(false);
        stage.show();
    }

    private void setDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMinWidth(1320);
        drawingPane.setMinHeight(900);
        drawingPane.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setVboxLayout() {
        this.vBox = new VBox();

        comboboxLabel = new Label("Choose figure to draw:");

        VBox.setMargin(comboboxLabel, new Insets(200,0,0,10));
        vBox.getChildren().add(comboboxLabel);

        figures = new ComboBox<>();
        figures.getItems().addAll("Pen", "Line", "Rectangle", "Circle");
        figures.setValue("Pen");
        this.curFigure = figures.getValue();

        VBox.setMargin(figures, new Insets(10,0,0,10));
        vBox.getChildren().add(figures);

        sliderLabel = new Label("Choose the size:");

        VBox.setMargin(sliderLabel, new Insets(50,0,0,10));
        vBox.getChildren().add(sliderLabel);

        sizeSlider = new Slider(1,10,1);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setBlockIncrement(1);
        sizeSlider.setMajorTickUnit(1);
        sizeSlider.setMinorTickCount(0);
        sizeSlider.setSnapToTicks(true);
        sizeSlider.setValue(3);
        this.curSize = (int)sizeSlider.getValue();

        VBox.setMargin(sizeSlider, new Insets(10,0,0,10));
        vBox.getChildren().add(sizeSlider);

        colorLabel = new Label("Choose the color:");

        VBox.setMargin(colorLabel, new Insets(50,0,0,10));
        vBox.getChildren().add(colorLabel);

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        this.curColor = colorPicker.getValue();

        VBox.setMargin(colorPicker, new Insets(10,0,0,10));
        vBox.getChildren().add(colorPicker);

        clearButton = new Button("Clear all");
        clearButton.setPrefSize(90,20);
        VBox.setMargin(clearButton, new Insets(40,0,0,10));
        vBox.getChildren().add(clearButton);

        saveButton = new Button("Save");
        saveButton.setPrefSize(90,20);
        VBox.setMargin(saveButton, new Insets(50,0,0,10));
        vBox.getChildren().add(saveButton);

        loadButton = new Button("Load");
        loadButton.setPrefSize(90,20);
        VBox.setMargin(loadButton, new Insets(20,0,0,10));
        vBox.getChildren().add(loadButton);

        vBox.setMinWidth(180);
    }

    private void setToolBarActions() {
        figures.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                curFigure = figures.getValue();
            }
        });

        sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                curSize = (int)sizeSlider.getValue();
            }
        });

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                curColor = colorPicker.getValue();
            }
        });

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                drawingPane.getChildren().clear();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showSaveDialog(null);
                if (file != null) {
                    try{
                        WritableImage writableImage = new WritableImage((int)drawingPane.getWidth(), (int)drawingPane.getHeight());
                        drawingPane.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (Exception e){
                        System.out.println("An error occurred while selecting file");
                        e.printStackTrace();
                    }
                }
            }
        });

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    try{
                        BufferedImage bufferedImage = ImageIO.read(file);
                        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
                        ImageView imageView = new ImageView(writableImage);

                        drawingPane.getChildren().clear();
                        drawingPane.getChildren().add(imageView);
                    } catch (Exception e) {
                        System.out.println("An error occurred while selecting file");
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void setDrawingActions() {
        drawingPane.setOnMousePressed(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if (x < 0 || y < 0){
                return;
            }
            switch (curFigure){
                case "Pen":
                    createPen(x, y);
                    break;
                case "Line":
                    createLine(x, y);
                    break;
                case "Rectangle":
                    createRectangle(x, y);
                    break;
                case "Circle":
                    createCircle(x, y);
                    break;
                default:
                    System.out.println("Something went wrong");
            }
        });

        drawingPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                if (x < 0 || y < 0){
                    return;
                }
                switch (curFigure){
                    case "Pen":
                        drawPen(x,y);
                        break;
                    case "Line":
                        drawLine(x,y);
                        break;
                    case "Rectangle":
                        drawRectangle(x,y);
                        break;
                    case "Circle":
                        drawCircle(x, y);
                        break;
                    default:
                        System.out.println("Smth went wrong");
                }
            }
        });
    }

    private void createPen(double x, double y) {
        point.setLocation(x,y);
    }

    private void drawPen(double x, double y) {
        Line line = new Line();
        line.setStartX(point.getX());
        line.setStartY(point.getY());
        line.setEndX(x);
        line.setEndY(y);
        point.setLocation(x,y);
        line.setStroke(curColor);
        line.setStrokeWidth(curSize);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        drawingPane.getChildren().add(line);
    }

    private void createLine(double x, double y) {
        Line line = new Line(x,y,x,y);
        line.setStroke(curColor);
        line.setStrokeWidth(curSize);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        drawingPane.getChildren().add(line);
    }

    private void drawLine(double x, double y) {
        Line line = (Line)drawingPane.getChildren().getLast();
        line.setEndX(x);
        line.setEndY(y);
    }
    private void createRectangle(double x, double y) {
        Rectangle rect = new Rectangle();
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(curColor);
        rect.setStrokeWidth(curSize);
        drawingPane.getChildren().add(rect);
        rect.setX(x);
        rect.setY(y);
        point.setLocation(x,y);
    }

    private void drawRectangle(double x, double y) {
        Rectangle rectangle = (Rectangle) drawingPane.getChildren().getLast();
        double px = point.getX();
        double py = point.getY();
        if (x >= point.getX() && y >= point.getY()) {
            rectangle.setWidth(x - px);
            rectangle.setHeight(y - py);
            return;
        }
        double xVert = Math.min(px, x);
        double yVert = Math.min(py, y);

        rectangle.setHeight(Math.abs(y - py));
        rectangle.setWidth(Math.abs(x - px));
        rectangle.setX(xVert);
        rectangle.setY(yVert);
    }

    private void createCircle(double x, double y) {
        Circle circle = new Circle(x, y ,0);
        circle.setStroke(curColor);
        circle.setStrokeWidth(curSize);
        circle.setFill(Color.TRANSPARENT);
        point.setLocation(x,y);

        drawingPane.getChildren().add(circle);
    }

    private void drawCircle(double x, double y) {
        Circle circle = (Circle) drawingPane.getChildren().getLast();
        double px = point.getX();
        double py = point.getY();
        double width = x - px;
        double height = y - py;
        double rad = Math.min(Math.abs(width), Math.abs(height)) / 2;
        circle.setRadius(rad);

        circle.setCenterX(width < 0 ? px - rad : px + rad);
        circle.setCenterY(height < 0 ? py - rad : py + rad);
    }
}
