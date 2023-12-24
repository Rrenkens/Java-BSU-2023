import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Pinta extends Application {
    private DataModel dataModel;
    private DrawingArea drawingArea;
    private DrawingAreaController drawingAreaController;
    private VBox root;
    private VBox top;
    private CustomMenuBar menu;
    private Icons icons;
    private InfoBar infoBar;
    private HBox center;
    private ToolsBar tools;
    private HBox bottom;
    private CustomColorPicker customColorPicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        dataModel = new DataModel();
        dataModel.stage = stage;

        drawingArea = new DrawingArea(dataModel);
        drawingAreaController = new DrawingAreaController(dataModel);

        root = new VBox(8);

        top = new VBox(8);
        icons = new Icons(dataModel);
        infoBar = new InfoBar(dataModel);

        center = new HBox(8);
        tools = new ToolsBar(dataModel);
        center.setPadding(new Insets(8));
        center.getChildren().addAll(tools, drawingArea);

        menu = new CustomMenuBar(dataModel);

        top.getChildren().addAll(menu, new Separator(), icons, infoBar);

        customColorPicker = new CustomColorPicker();
        dataModel.customColorPicker = customColorPicker;
        bottom = new HBox();
        bottom.setPadding(new Insets(0, 0, 8, 8));
        bottom.getChildren().addAll(customColorPicker);

        root.getChildren().addAll(top, new Separator(), center, bottom);


        Scene scene = new Scene(root, 800, 620);

        stage.setScene(scene);
        stage.setTitle("Unsaved image - Pinta");
        stage.centerOnScreen();
        stage.show();
    }
}