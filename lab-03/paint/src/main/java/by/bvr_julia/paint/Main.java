package by.bvr_julia.paint;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.stage.FileChooser;

public class Main extends Application {

    private Double x1, x2, y1, y2;
    private boolean rubber = false;
    private boolean rect = false;
    private boolean circle = false;
    private boolean rectF = false;
    private boolean circleF = false;
    private boolean paint = true;

    private WritableImage snapshot;

    public static void main(String[] args) {

        Application.launch(args);
    }

    private void reset(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void start(Stage stage) throws Exception {

        Brush brush = new Brush();

        Label label = new Label("Choose brush size");
        label.setFont(new Font(20));
        Slider slider = new Slider(5, 30, 10);
        slider.valueProperty().addListener((changed, oldValue, newValue) -> {
            brush.setSize(newValue.intValue());
            label.setText("Brush size is " + newValue.intValue());
        });
        Label label2 = new Label("Choose brush color");
        label2.setFont(new Font(20));
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(event -> brush.setColor(colorPicker.getValue()));

        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/brush1.png")));
        Image img1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/rubber1.png")));
        Image img2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/rect1.png")));
        Image img3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/circle1.png")));
        Image img4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/rectF1.png")));
        Image img5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/circleF1.png")));

        Image imgS = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/save1.png")));
        Image imgL = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/load1.png")));
        Image imgC = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/clear1.png")));

        Label label3 = new Label("Modes");
        label3.setFont(new Font(20));
        ToggleButton btn = new ToggleButton("Brush", new ImageView(img));
        ToggleButton btn1 = new ToggleButton("Rubber", new ImageView(img1));
        ToggleButton btn2 = new ToggleButton("Rectangular", new ImageView(img2));
        ToggleButton btn3 = new ToggleButton("Circle", new ImageView(img3));
        ToggleButton btn4 = new ToggleButton("Filled rectangular", new ImageView(img4));
        ToggleButton btn5 = new ToggleButton("Filled circle", new ImageView(img5));
        ToggleGroup group = new ToggleGroup();
        Label label4 = new Label("Work with file");
        label4.setFont(new Font(20));
        Button save = new Button("Save to file", new ImageView(imgS));
        Button load = new Button("Load file", new ImageView(imgL));
        Button clear = new Button("Clear", new ImageView(imgC));
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn1.setAlignment(Pos.BASELINE_LEFT);
        btn2.setAlignment(Pos.BASELINE_LEFT);
        btn3.setAlignment(Pos.BASELINE_LEFT);
        btn4.setAlignment(Pos.BASELINE_LEFT);
        btn5.setAlignment(Pos.BASELINE_LEFT);
        save.setAlignment(Pos.BASELINE_LEFT);
        load.setAlignment(Pos.BASELINE_LEFT);
        clear.setAlignment(Pos.BASELINE_LEFT);

        btn5.setTooltip(new Tooltip("To draw figure push the mouse a the one corner of the figure and drag it to the opposite corner"));
        btn2.setTooltip(new Tooltip("To draw figure push the mouse a the one corner of the figure and drag it to the opposite corner"));
        btn3.setTooltip(new Tooltip("To draw figure push the mouse a the one corner of the figure and drag it to the opposite corner"));
        btn4.setTooltip(new Tooltip("To draw figure push the mouse a the one corner of the figure and drag it to the opposite corner"));
        btn.setToggleGroup(group);
        btn1.setToggleGroup(group);
        btn2.setToggleGroup(group);
        btn3.setToggleGroup(group);
        btn4.setToggleGroup(group);
        btn5.setToggleGroup(group);
        btn.setSelected(true);
        group.selectedToggleProperty().addListener((changed, oldValue, newValue) -> {
            rubber = newValue == btn1;
            rect = newValue == btn2;
            circle = newValue == btn3;
            rectF = newValue == btn4;
            circleF = newValue == btn5;
            paint = newValue == btn;
            if (newValue == null){
                btn.setSelected(true);
                paint = true;
            }
        });



        VBox root = new VBox(label, slider, label2, colorPicker, label3, btn, btn1, btn2, btn3, btn4, btn5, label4,save,load, clear);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxWidth(Double.MAX_VALUE);
        btn5.setMaxWidth(Double.MAX_VALUE);
        save.setMaxWidth(Double.MAX_VALUE);
        load.setMaxWidth(Double.MAX_VALUE);
        clear.setMaxWidth(Double.MAX_VALUE);
        colorPicker.setMaxWidth(Double.MAX_VALUE);
        root.setPadding(new Insets(10));
        GridPane root1 = new GridPane();
        root1.add(root, 0, 0);
        Canvas canvas = new Canvas(1000,650);

        ScrollPane scroll = new ScrollPane(canvas);
        scroll.setPrefViewportHeight(canvas.getHeight());
        scroll.setPrefViewportWidth(canvas.getWidth());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                e -> {
                    if (!rubber) {
                        gc.setStroke(brush.getColor());
                        gc.setFill(brush.getColor());
                    } else {
                        gc.setStroke(Color.WHITE);
                        gc.setFill(Color.WHITE);
                    }
                    gc.setLineWidth(brush.getSize());
                    if (!circle && !rect && !circleF && !rectF) {
                        x1 = e.getX();
                        y1 = e.getY();
                        gc.strokeLine(x1, y1, x2, y2);
                        gc.setLineCap(StrokeLineCap.ROUND);
                        x2 = x1;
                        y2 = y1;
                    } else {

                        gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
                        x1 = e.getX();
                        y1 = e.getY();
                        Double xTmp = x2;
                        Double yTmp = y2;
                        if (x1 > xTmp) {
                            Double tmp = x1;
                            x1 = xTmp;
                            xTmp = tmp;
                        }
                        if (y1 > yTmp) {
                            Double tmp = y1;
                            y1 = yTmp;
                            yTmp = tmp;
                        }
                        gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
                        if (circle) {
                            gc.strokeOval(x1, y1, xTmp - x1, yTmp - y1);
                        } else if (rect) {
                            gc.strokeRect(x1, y1, xTmp - x1, yTmp - y1);
                        } else if (rectF) {
                            gc.fillRect(x1, y1, xTmp - x1, yTmp - y1);
                        } else if (circleF) {
                            gc.fillOval(x1, y1, xTmp - x1, yTmp - y1);
                        }
                    }

                });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    x1 = e.getX();
                    y1 = e.getY();

                    x2 = x1;
                    y2 = y1;

                    WritableImage writableImage = new WritableImage((int)Math.rint(2.0*canvas.getWidth()), (int)Math.rint(2.0*canvas.getHeight()));
                    SnapshotParameters spa = new SnapshotParameters();
                    spa.setTransform(Transform.scale(2.0, 2.0));
                    snapshot = canvas.snapshot(spa, writableImage);
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                e -> {
                    if (!rubber) {
                        gc.setStroke(brush.getColor());
                        gc.setFill(brush.getColor());
                    } else {
                        gc.setStroke(Color.WHITE);
                        gc.setFill(Color.WHITE);
                    }
                    gc.setLineWidth(brush.getSize());
                    x1 = e.getX();
                    y1 = e.getY();
                    if (x1 > x2) {
                        Double tmp = x1;
                        x1 = x2;
                        x2 = tmp;
                    }
                    if (y1 > y2) {
                        Double tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }

                    if (circle) {
                        gc.drawImage(snapshot, 0, 0,  canvas.getWidth(),canvas.getHeight());
                        gc.strokeOval(x1, y1, x2 - x1, y2 - y1);
                    } else if (rect) {
                        gc.drawImage(snapshot, 0, 0,  canvas.getWidth(),canvas.getHeight());
                        gc.strokeRect(x1, y1, x2 - x1, y2 - y1);
                    } else if (rectF) {
                        gc.drawImage(snapshot, 0, 0,  canvas.getWidth(),canvas.getHeight());
                        gc.fillRect(x1, y1, x2 - x1, y2 - y1);
                    } else if (circleF) {
                        gc.drawImage(snapshot, 0, 0,  canvas.getWidth(),canvas.getHeight());
                        gc.fillOval(x1, y1, x2 - x1, y2 - y1);
                    }
                    if (!circle && !rect && !circleF && !rectF) {
                        gc.fillOval(e.getX() - brush.getSize() / 2, e.getY() - brush.getSize() / 2, brush.getSize(), brush.getSize());
                    }

                });

        save.setOnAction((e) -> {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            savefile.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png"));

            File file = savefile.showSaveDialog(stage);

            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int)Math.rint(2.0*canvas.getWidth()), (int)Math.rint(2.0*canvas.getHeight()));
                    SnapshotParameters spa = new SnapshotParameters();
                    spa.setTransform(Transform.scale(2.0, 2.0));
                    canvas.snapshot(spa, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error!");
                }
            }
        });
        load.setOnAction((e) -> {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Choose File");

            File file = savefile.showOpenDialog(stage);
            if (file != null) {
                BufferedImage image = null;
                try {
                    image = ImageIO.read(file);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Image im = SwingFXUtils.toFXImage(image, null);
                double k1 = im.getHeight()/ canvas.getHeight();
                double k2 = im.getWidth()/canvas.getWidth();
                double k = 1;
                if(k1>1 || k2>1){
                    k = Math.max(k2, k1);
                }
                if(k>1) {
                    double targetWidth = im.getWidth() / k;
                    double targetHeight = im.getHeight() / k;
                    ImageView imageView = new ImageView(im);
                    imageView.setFitHeight(targetHeight);
                    imageView.setFitWidth(targetWidth);
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(imageView.getImage(), 0, 0, targetWidth, targetHeight);
                }else{
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(im, 0, 0, im.getWidth(), im.getHeight());
                }
            }
        });
        clear.setOnAction((e) -> {
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.WHITE);
            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        });


        root1.add(scroll, 1, 0);
        Scene scene = new Scene(root1);

        stage.setScene(scene);
        stage.setTitle("Paint");
        stage.getIcons().add(new Image("/brush1.png"));
        stage.centerOnScreen();
        stage.show();
    }
}