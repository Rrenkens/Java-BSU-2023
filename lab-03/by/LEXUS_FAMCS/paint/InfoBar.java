package by.LEXUS_FAMCS.paint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class InfoBar extends HBox {
    private final DataModel dataModel;
    private final Label label;
    private final HBox brushTools;
    private final HBox shapeTools;
    private final ImageView imageView;
    private TextField sizeOfBrush;
    private Button incButton;
    private Button decButton;
    private CheckBox fillCheckBox;
    InfoBar(DataModel dataModel) {
        this.dataModel = dataModel;

        setSpacing(8);
        setPadding(new Insets(0 ,0 ,0, 8));

        brushTools = new HBox();
        setBrushTools();
        shapeTools = new HBox(8);
        setShapeTools();

        label = new Label("Instrument:");
        label.setFont(DataModel.font);
        label.setPrefHeight(sizeOfBrush.getHeight() + 22);

        imageView = new ImageView("images/Pinta/brush.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        getChildren().addAll(label, imageView, brushTools);

        dataModel.sizeOfBrush = sizeOfBrush;
        dataModel.fillCheckBox = fillCheckBox;
        dataModel.infoBar = this;
    }

    private void setBrushTools() {
        sizeOfBrush = new TextField("3");
        sizeOfBrush.setPrefWidth(80);
        Label label = new Label("Size of brush: ");
        label.setPrefHeight(sizeOfBrush.getHeight() + 22);
        label.setFont(DataModel.font);
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
                    newValue = newValue.replaceAll("^0+|\\D", "");
                }
                if (!newValue.isEmpty()) {
                    sizeOfBrush.setText(Integer.parseInt(newValue) > 1000 ? oldValue : newValue);
                } else {
                    sizeOfBrush.setText("");
                }
            }
        });

        brushTools.getChildren().addAll(label, sizeOfBrush, incButton, decButton);
    }

    private void setShapeTools() {

        Label fillLabel = new Label("Fill figure");
        fillCheckBox = new CheckBox();
        fillLabel.setFont(DataModel.font);
        fillLabel.setPrefHeight(sizeOfBrush.getHeight() + 22);
        fillCheckBox.setPrefHeight(sizeOfBrush.getPrefHeight() + 25);
        shapeTools.getChildren().addAll(fillLabel, fillCheckBox);
    }

    void infoSetter(String id) {
        switch (id) {
            case "brush" -> {
                id = "images/Pinta/brush.png";
                var info = getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                info.remove(shapeTools);
            }
            case "pencil" -> {
                id = "images/Pinta/pencil.png";
                var info = getChildren();
                info.remove(brushTools);
                info.remove(shapeTools);
            }
            case "rect" -> {
                id = "images/Pinta/rect.png";
                var info = getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                if (!info.contains(shapeTools)) {
                    info.add(shapeTools);
                }
            }
            case "oval" -> {
                id = "images/Pinta/oval.png";
                var info = getChildren();
                if (!info.contains(brushTools)) {
                    info.add(brushTools);
                }
                if (!info.contains(shapeTools)) {
                    info.add(shapeTools);
                }
            }
            case "" -> {
                getChildren().remove(brushTools);
                getChildren().remove(shapeTools);
            }
            //TODO
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
        imageView.setImage(id.isEmpty() ? null : new Image(id));
    }
}
