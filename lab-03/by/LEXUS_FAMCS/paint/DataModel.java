package by.LEXUS_FAMCS.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class DataModel {
    Stage stage;
    Loader loader;
    Saver saver;
    Canvas drawCanvas;
    GraphicsContext gcDraw;
    Canvas tempCanvas;
    GraphicsContext gcTemp;
    Canvas borderCanvas;
    GraphicsContext gcBorder;
    CustomColorPicker customColorPicker;

    CheckBox fillCheckBox;
    TextField sizeOfBrush;

    File toFile;

    InfoBar infoBar;

    ToolsBar tools;
    List<Integer> indexes;

    static final Font font = Font.font("Arial", FontWeight.BOLD, 14);
}
