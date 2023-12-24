import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Pinta extends Application {
    private double lastX, lastY;
    private double startX, startY;
    private boolean isShapeSelected = false;
    private File toFile;

    private final Font font = Font.font("Arial", FontWeight.BOLD, 14);

    private Stage stage;
    private VBox root;
    private VBox top;
    private MenuBar menu;
    private Loader loader;
    private Saver saver;
    private HBox icons;
    private HBox infoBar;
    private ImageView imageView;
    private HBox brushTools;
    private HBox shapeTools;
    private CheckBox fillCheckBox;
    private TextField sizeOfBrush;
    private Button incButton;
    private Button decButton;
    private HBox center;
    private VBox tools;
    private List<Integer> indexes;
    private StackPane canvasStack;
    private Canvas drawCanvas;
    private Canvas tempCanvas;
    
    private GraphicsContext gcDraw;
    private GraphicsContext gcTemp;
    private HBox bottom;
    private CustomColorPicker customColorPicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        root = new VBox(8);

        top = new VBox(8);
        createIcons();
        createBrushTools();
        createShapeTools();
        createInfoBar();

        center = new HBox(8);
        createTools();
        createCanvas();
        canvasStack = new StackPane();
        canvasStack.getChildren().addAll(drawCanvas, tempCanvas);
        center.setPadding(new Insets(8));
        //TODO
        // HBox.setHgrow(drawCanvas, Priority.ALWAYS);
        center.getChildren().addAll(tools, canvasStack);

        createMenu();
        top.getChildren().addAll(menu, new Separator(), icons, infoBar);

        customColorPicker = new CustomColorPicker();
        bottom = new HBox();
        bottom.setPadding(new Insets(0, 0, 0, 8));
        bottom.getChildren().addAll(customColorPicker);

        root.getChildren().addAll(top, new Separator(), center, bottom);


        Scene scene = new Scene(root, 800, 620);

        stage.setScene(scene);
        stage.setTitle("Unsaved image - Pinta");
        stage.centerOnScreen();
        stage.show();
    }

    private void createInfoBar() {
        infoBar = new HBox(8);
        infoBar.setPadding(new Insets(0 ,0 ,0, 8));

        Label label = new Label("Instrument:");
        label.setFont(font);
        label.setPrefHeight(sizeOfBrush.getHeight() + 22);

        imageView = new ImageView("images/Pinta/brush.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        infoBar.getChildren().addAll(label, imageView, brushTools, shapeTools);
    }

    private void createBrushTools() {
        brushTools = new HBox();

        sizeOfBrush = new TextField("3");
        sizeOfBrush.setPrefWidth(80);
        Label label = new Label("Size of brush: ");
        label.setPrefHeight(sizeOfBrush.getHeight() + 22);
        label.setFont(font);
        incButton = new Button("+");
        decButton = new Button("-");
        incButton.setPrefWidth(30);
        decButton.setPrefWidth(incButton.getPrefWidth());
        incButton.setOnAction(e -> {
            int size = Integer.parseInt(sizeOfBrush.getText());
            if (size != 1000) {
                sizeOfBrush.setText(String.valueOf(size + 1));
            }
        });
        decButton.setOnAction(e -> {
            int size = Integer.parseInt(sizeOfBrush.getText());
            if (size != 1) {
                sizeOfBrush.setText(String.valueOf(size - 1));
            }
        });
        sizeOfBrush.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.matches("^[1-9]\\d*$")) {
                    newValue = newValue.replaceAll("^0+|[^\\d]", "");
                }
                if (!newValue.isEmpty()) {
                    sizeOfBrush.setText(Integer.parseInt(newValue) > 1000 ? oldValue : newValue);
                }
            }
        });

        brushTools.getChildren().addAll(label, sizeOfBrush, incButton, decButton);
    }

    private void createShapeTools() {
        shapeTools = new HBox(8);

        Label fillLabel = new Label("Fill figure");
        fillCheckBox = new CheckBox();
        fillLabel.setFont(font);
        fillLabel.setPrefHeight(sizeOfBrush.getHeight() + 22);
        fillCheckBox.setPrefHeight(sizeOfBrush.getPrefHeight() + 25);
        shapeTools.getChildren().addAll(fillLabel, fillCheckBox);
    }
    private void createMenu() {
        menu = new MenuBar();
        loader = new Loader(drawCanvas);
        saver = new Saver(drawCanvas);
        
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open...");
        MenuItem save = new MenuItem("Save...");
        MenuItem saveAs = new MenuItem("Save as...");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().addAll(open, new SeparatorMenuItem(), save, saveAs, new SeparatorMenuItem(), exit);

        menu.getMenus().addAll(file);

        open.setOnAction(e -> {
            loader.load();
        });

        saveAs.setOnAction(e -> {
            toFile = saver.save();
            if (toFile != null) {
                stage.setTitle(toFile.getName() + " - Pinta");
            }
        });

        save.setOnAction(e -> {
            try {
                if (toFile != null) {
                    saver.saveIntoFile(toFile);
                } else {
                    toFile = saver.save();
                }
                stage.setTitle(toFile.getName() + " - Pinta");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });
    }
    private void createCanvas() {
        drawCanvas = new Canvas();
        gcDraw = drawCanvas.getGraphicsContext2D();
        gcDraw.setLineCap(StrokeLineCap.ROUND);
        gcDraw.setLineJoin(StrokeLineJoin.ROUND);
        drawCanvas.setWidth(600);
        drawCanvas.setHeight(400);

        tempCanvas = new Canvas();
        gcTemp = tempCanvas.getGraphicsContext2D();
        tempCanvas.setWidth(drawCanvas.getWidth());
        tempCanvas.setHeight(drawCanvas.getHeight());

        gcDraw.setStroke(Color.BLACK);
        gcDraw.setLineWidth(2);
        gcDraw.strokeRect(drawCanvas.getLayoutX(), drawCanvas.getLayoutY(), drawCanvas.getWidth(), drawCanvas.getHeight());

        createCanvasEvents();
    }

    private void createCanvasEvents() {
        //TODO
        tempCanvas.setOnMouseClicked(e -> {
            String id = "";
            for (var elem : tools.getChildren()) {
                if (((ToggleButton) elem).isSelected()) {
                    id = elem.getId();
                    break;
                }
            }
            mouseClickedChooser(e, id);
        });
        tempCanvas.setOnMousePressed(e -> {
            lastX = startX = e.getX();
            lastY = startY = e.getY();
        });

        tempCanvas.setOnMouseDragged(e -> {
            String id = "";
            for (var elem : tools.getChildren()) {
                if (((ToggleButton) elem).isSelected()) {
                    id = elem.getId();
                    break;
                }
            }
            mouseDraggedChooser(e, id);
        });

        tempCanvas.setOnMouseReleased(e -> {
            if (isShapeSelected) {
                var shapes = tools.getChildren();
                for (int ind : indexes) {
                    if (((ToggleButton) shapes.get(ind)).isSelected()) {
                        mouseReleasedChooser(e, ((ToggleButton) shapes.get(ind)).getId());
                    }
                }
            }
        });
    }

//    private void setTitleModified(boolean isModified) {
//        String title = stage.getTitle();
//        int pos = title.lastIndexOf(' ') - 2;
//        if (!isModified) {
//            stage.setTitle(title.substring(0, title.lastIndexOf('*')) + " - Pinta");
//        }
//        if (title.charAt(pos - 1) != '*') {
//            stage.setTitle(title.substring(0, title.lastIndexOf(' ') - 2) + "* - Pinta");
//        }
//    }

    private void mouseClickedChooser(MouseEvent e, String id) {
        switch (id) {
            case "brush" -> {
//                setTitleModified(hasSomeChanges());
                try {
                    gcDraw.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                }
                gcDraw.strokeOval(e.getX(), e.getY(), gcDraw.getLineWidth(), gcDraw.getLineWidth());
            }
            case "pencil" -> {
//                setTitleModified();
                gcDraw.setLineWidth(1);
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                }
                gcDraw.strokeOval(e.getX(), e.getY(), 1, 1);
            }
            //TODO
            default -> {}
        }
    }

    private void mouseDraggedChooser(MouseEvent e, String id) {
        switch (id) {
            case "brush" -> {
//                setTitleModified();
                isShapeSelected = false;
                try {
                    gcDraw.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                }
                double x = e.getX();
                double y = e.getY();
                gcDraw.strokeLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
            case "pencil" -> {
//                setTitleModified();
                isShapeSelected = false;
                gcDraw.setLineWidth(1);
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                }
                double x = e.getX();
                double y = e.getY();
                gcDraw.strokeLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
            case "rect" -> {
//                setTitleModified();
                isShapeSelected = true;
                gcTemp.clearRect(0 , 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                lastX = e.getX();
                lastY = e.getY();
                try {
                    gcTemp.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcTemp.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcTemp.setStroke(customColorPicker.getMainColor());
                    gcTemp.setFill(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcTemp.setStroke(customColorPicker.getExtraColor());
                    gcTemp.setFill(customColorPicker.getExtraColor());
                }
                if (fillCheckBox.isSelected()) {
                    gcTemp.fillRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    gcTemp.strokeRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            case "oval" -> {
//                setTitleModified();
                isShapeSelected = true;
                gcTemp.clearRect(0 , 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                lastX = e.getX();
                lastY = e.getY();
                try {
                    gcTemp.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcTemp.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcTemp.setStroke(customColorPicker.getMainColor());
                    gcTemp.setFill(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcTemp.setStroke(customColorPicker.getExtraColor());
                    gcTemp.setFill(customColorPicker.getExtraColor());
                }
                if (fillCheckBox.isSelected()) {
                    gcTemp.fillOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    gcTemp.strokeOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            //TODO
            default -> {}
        }
    }

    private void mouseReleasedChooser(MouseEvent e, String id) {
        switch (id) {
            case "rect" -> {
                gcTemp.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                try {
                    gcDraw.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                    gcDraw.setFill(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                    gcDraw.setFill(customColorPicker.getExtraColor());
                }
                if (fillCheckBox.isSelected()) {
                    gcDraw.fillRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    gcDraw.strokeRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            case "oval" -> {
                gcTemp.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                try {
                    gcDraw.setLineWidth(Integer.parseInt(sizeOfBrush.getText()));
                } catch (Exception exc) {
                    gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    gcDraw.setStroke(customColorPicker.getMainColor());
                    gcDraw.setFill(customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    gcDraw.setStroke(customColorPicker.getExtraColor());
                    gcDraw.setFill(customColorPicker.getExtraColor());
                }
                if (fillCheckBox.isSelected()) {
                    gcDraw.fillOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    gcDraw.strokeOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            //TODO
            default -> {}
        }
    }

//    private boolean hasSomeChanges() {
//        Image image1 = toFile == null ?
//                (new Canvas(drawCanvas.getWidth(), drawCanvas.getHeight())).snapshot(null, null)
//                : new Image(toFile.toURI().toString());
//        Image image2 = drawCanvas.snapshot(null, null);
//
//        int width = (int) image1.getWidth();
//        int height = (int) image1.getHeight();
//
//        if (width != image2.getWidth() || height != image2.getHeight()) {
//            return true;
//        }
//
//        PixelReader reader1 = image1.getPixelReader();
//        PixelReader reader2 = image2.getPixelReader();
//
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (reader1.getArgb(x, y) != reader2.getArgb(x, y)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

    private void infoSetter(String id) {
        switch (id) {
            case "brush" -> {
                id = "images/Pinta/brush.png";
                var info = infoBar.getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                info.remove(shapeTools);
            }
            case "pencil" -> {
                id = "images/Pinta/pencil.png";
                var info = infoBar.getChildren();
                info.remove(brushTools);
                info.remove(shapeTools);
            }
            case "rect" -> {
                id = "images/Pinta/rect.png";
                var info = infoBar.getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                if (!info.contains(shapeTools)) {
                    info.add(shapeTools);
                }
            }
            case "oval" -> {
                id = "images/Pinta/oval.png";
                var info = infoBar.getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                if (!info.contains(shapeTools)) {
                    info.add(shapeTools);
                }
            }
            case "" -> {
                infoBar.getChildren().remove(brushTools);
                infoBar.getChildren().remove(shapeTools);
            }
            //TODO
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
        imageView.setImage(id.isEmpty() ? null : new Image(id));
    }
    private void createIcons() {
        icons = new HBox();
        icons.setPadding(new Insets(0, 0, 0, 8));
        
        Button create = new Button();
        ImageView createImageView = new ImageView(new Image("/images/Pinta/create.png"));
        createImageView.setFitWidth(30);
        createImageView.setFitHeight(30);
        create.setStyle("-fx-background-color: transparent;");
        create.setGraphic(createImageView);
        create.setPrefSize(createImageView.getFitWidth() + 13, createImageView.getFitHeight() + 13);

        Button open = new Button();
        ImageView openImageView = new ImageView(new Image("/images/Pinta/open.png"));
        openImageView.setFitWidth(createImageView.getFitWidth() + 4);
        openImageView.setFitHeight(createImageView.getFitHeight() + 4);
        open.setStyle("-fx-background-color: transparent;");
        open.setGraphic(openImageView);
        open.setText("  Open");
        open.setFont(font);

        Button save = new Button();
        ImageView saveImageView = new ImageView(new Image("/images/Pinta/save.png"));
        saveImageView.setFitWidth(createImageView.getFitWidth());
        saveImageView.setFitHeight(createImageView.getFitHeight());
        save.setStyle("-fx-background-color: transparent;");
        save.setGraphic(saveImageView);
        save.setText("  Save");
        save.setFont(font);
        save.setPrefSize(100, openImageView.getFitHeight() + 10);

        Button paste = new Button();
        ImageView pasteImageView = new ImageView(new Image("/images/Pinta/paste.png"));
        pasteImageView.setFitWidth(createImageView.getFitWidth() + 4);
        pasteImageView.setFitHeight(createImageView.getFitHeight());
        paste.setStyle("-fx-background-color: transparent;");
        paste.setGraphic(pasteImageView);
        paste.setText("  Paste");
        paste.setFont(font);
        paste.setPrefSize(115, openImageView.getFitHeight() + 10);

        Button clear = new Button();
        ImageView clearImageView = new ImageView("/images/Pinta/clear.png");
        clearImageView.setFitWidth(createImageView.getFitWidth() + 4);
        clearImageView.setFitHeight(createImageView.getFitHeight());
        clear.setStyle("-fx-background-color: transparent;");
        clear.setGraphic(clearImageView);
        clear.setText("  Clear");
        clear.setFont(font);
        clear.setPrefSize(115, openImageView.getFitHeight() + 10);

        create.setOnAction(e -> {

        });
        create.setOnMouseEntered(e -> {
            create.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        create.setOnMouseExited(e -> {
            create.setStyle("-fx-background-color: transparent;");
        });

        open.setOnAction(e -> System.out.println("Open..."));
        open.setOnMouseEntered(e -> {
            open.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        open.setOnMouseExited(e -> {
            open.setStyle("-fx-background-color: transparent;");
        });

        save.setOnAction(e -> System.out.println("Save..."));
        save.setOnMouseEntered(e -> {
            save.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        save.setOnMouseExited(e -> {
            save.setStyle("-fx-background-color: transparent;");
        });

        paste.setOnAction(e -> System.out.println("Paste..."));
        paste.setOnMouseEntered(e -> {
            paste.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        paste.setOnMouseExited(e -> {
            paste.setStyle("-fx-background-color: transparent;");
        });

        clear.setOnAction(e -> {
//            setTitleModified();
            gcDraw.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
            gcDraw.setLineWidth(1);
            gcDraw.setStroke(Color.BLACK);
            gcDraw.strokeRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
        });
        clear.setOnMouseEntered(e -> {
            clear.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        clear.setOnMouseExited(e -> {
            clear.setStyle("-fx-background-color: transparent;");
        });

        icons.getChildren().addAll(create, open, save, new Separator(Orientation.VERTICAL), paste, new Separator(Orientation.VERTICAL), clear);
    }

    private void createTools() {
        tools = new VBox(10);
        
        ToggleGroup instrGroup = new ToggleGroup();
        
        ToggleButton brush = new ToggleButton();
        brush.setId("brush");
        brush.fire();
        brush.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        ImageView brushImageView = new ImageView(new Image("/images/Pinta/brush.png"));
        brushImageView.setFitWidth(28);
        brushImageView.setFitHeight(29);


        brush.setGraphic(brushImageView);
        brush.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        ToggleButton pencil = new ToggleButton();
        pencil.setId("pencil");
        ImageView pencilImageView = new ImageView(new Image("/images/Pinta/pencil.png"));
        pencilImageView.setFitWidth(brushImageView.getFitWidth());
        pencilImageView.setFitHeight(brushImageView.getFitHeight());
        pencil.setStyle("-fx-background-color: transparent;");
        pencil.setGraphic(pencilImageView);
        pencil.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        ToggleButton rect = new ToggleButton();
        rect.setId("rect");
        ImageView rectImageView = new ImageView(new Image("/images/Pinta/rect.png"));
        rectImageView.setFitWidth(brushImageView.getFitWidth());
        rectImageView.setFitHeight(brushImageView.getFitHeight());
        rect.setStyle("-fx-background-color: transparent;");
        rect.setGraphic(rectImageView);
        rect.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        ToggleButton oval = new ToggleButton();
        oval.setId("oval");
        ImageView ovalImageView = new ImageView(new Image("/images/Pinta/oval.png"));
        ovalImageView.setFitWidth(30);
        ovalImageView.setFitHeight(30);
        oval.setStyle("-fx-background-color: transparent;");
        oval.setGraphic(ovalImageView);
        oval.setPrefSize(brushImageView.getFitWidth() + 10, brushImageView.getFitHeight() + 13);

        brush.setOnMouseEntered(e -> {
            brush.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        brush.setOnMouseExited(e -> {
            if (!brush.isSelected()) {
                brush.setStyle("-fx-background-color: transparent;");
                brush.setDisable(false);
            };
        });

        pencil.setOnMouseEntered(e -> {
            pencil.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        pencil.setOnMouseExited(e -> {
            if (!pencil.isSelected()) {
                pencil.setStyle("-fx-background-color: transparent;");
            }
        });

        rect.setOnMouseEntered(e -> {
            rect.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        rect.setOnMouseExited(e -> {
            if (!rect.isSelected()) {
                rect.setStyle("-fx-background-color: transparent;");
            }
        });

        oval.setOnMouseEntered(e -> {
            oval.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        });
        oval.setOnMouseExited(e -> {
            if (!oval.isSelected()) {
                oval.setStyle("-fx-background-color: transparent;");
            }
        });

        instrGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                ((ToggleButton) oldValue).setStyle("-fx-background-color: transparent;");
            }
            if (newValue != null) {
                ((ToggleButton) newValue).setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
                infoSetter(((ToggleButton) newValue).getId());
            } else {
                infoSetter("");
            }
        });

        instrGroup.getToggles().addAll(brush, pencil, rect, oval);
        indexes = List.of(2, 3);
        tools.getChildren().addAll(brush, pencil, rect, oval);
    }
}