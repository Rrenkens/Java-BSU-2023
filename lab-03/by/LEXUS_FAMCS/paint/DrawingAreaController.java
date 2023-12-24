package by.LEXUS_FAMCS.paint;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawingAreaController {
    DataModel dataModel;

    private double lastX, lastY;
    private double startX, startY;
    private boolean isShapeSelected = false;
    
    DrawingAreaController(DataModel dataModel) {
        this.dataModel = dataModel;

        createSizeEvents();
        createCanvasEvents();
    }

    private void createSizeEvents() {
        dataModel.stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            dataModel.gcBorder.clearRect(0, 0, dataModel.borderCanvas.getWidth(), dataModel.borderCanvas.getHeight());
            Image image = dataModel.drawCanvas.snapshot(null, null);
            dataModel.gcDraw.clearRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
            dataModel.drawCanvas.setWidth(newWidth.doubleValue() - 100);
            dataModel.tempCanvas.setWidth(dataModel.drawCanvas.getWidth());
            dataModel.borderCanvas.setWidth(dataModel.drawCanvas.getWidth());
            dataModel.gcBorder.setStroke(Color.BLACK);
            dataModel.gcBorder.setLineWidth(2);
            dataModel.gcDraw.drawImage(image, 0, 0);
            dataModel.gcBorder.strokeRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
        });

        dataModel.stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            dataModel.gcBorder.clearRect(0, 0, dataModel.borderCanvas.getWidth(), dataModel.borderCanvas.getHeight());
            Image image = dataModel.drawCanvas.snapshot(null, null);
            dataModel.gcDraw.clearRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
            dataModel.drawCanvas.setHeight(newHeight.doubleValue() - 260);
            dataModel.tempCanvas.setHeight(dataModel.drawCanvas.getHeight());
            dataModel.borderCanvas.setHeight(dataModel.drawCanvas.getHeight());
            dataModel.gcBorder.setStroke(Color.BLACK);
            dataModel.gcBorder.setLineWidth(2);
            dataModel.gcDraw.drawImage(image, 0, 0);
            dataModel.gcBorder.strokeRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
        });
    }

    private void createCanvasEvents() {
        //TODO
        dataModel.tempCanvas.setOnMouseClicked(e -> {
            String id = "";
            for (var elem : dataModel.tools.getChildren()) {
                if (((ToggleButton) elem).isSelected()) {
                    id = elem.getId();
                    break;
                }
            }
            mouseClickedChooser(e, id);
        });
        dataModel.tempCanvas.setOnMousePressed(e -> {
            lastX = startX = e.getX();
            lastY = startY = e.getY();
        });

        dataModel.tempCanvas.setOnMouseDragged(e -> {
            String id = "";
            for (var elem : dataModel.tools.getChildren()) {
                if (((ToggleButton) elem).isSelected()) {
                    id = elem.getId();
                    break;
                }
            }
            mouseDraggedChooser(e, id);
        });

        dataModel.tempCanvas.setOnMouseReleased(e -> {
            if (isShapeSelected) {
                var shapes = dataModel.tools.getChildren();
                for (int ind : dataModel.indexes) {
                    if (((ToggleButton) shapes.get(ind)).isSelected()) {
                        mouseReleasedChooser(e, ((ToggleButton) shapes.get(ind)).getId());
                    }
                }
            }
        });
    }

    private void mouseClickedChooser(MouseEvent e, String id) {
        switch (id) {
            case "brush" -> {
                try {
                    dataModel.gcDraw.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                }
                dataModel.gcDraw.strokeOval(e.getX(), e.getY(), dataModel.gcDraw.getLineWidth(), dataModel.gcDraw.getLineWidth());
            }
            case "pencil" -> {
                dataModel.gcDraw.setLineWidth(1);
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                }
                dataModel.gcDraw.strokeOval(e.getX(), e.getY(), 1, 1);
            }
            //TODO
            default -> {}
        }
    }

    private void mouseDraggedChooser(MouseEvent e, String id) {
        switch (id) {
            case "brush" -> {
                isShapeSelected = false;
                try {
                    dataModel.gcDraw.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                }
                double x = e.getX();
                double y = e.getY();
                dataModel.gcDraw.strokeLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
            case "pencil" -> {
                isShapeSelected = false;
                dataModel.gcDraw.setLineWidth(1);
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                }
                double x = e.getX();
                double y = e.getY();
                dataModel.gcDraw.strokeLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
            case "rect" -> {
                isShapeSelected = true;
                dataModel.gcTemp.clearRect(0 , 0, dataModel.tempCanvas.getWidth(), dataModel.tempCanvas.getHeight());
                lastX = e.getX();
                lastY = e.getY();
                try {
                    dataModel.gcTemp.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcTemp.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcTemp.setStroke(dataModel.customColorPicker.getMainColor());
                    dataModel.gcTemp.setFill(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcTemp.setStroke(dataModel.customColorPicker.getExtraColor());
                    dataModel.gcTemp.setFill(dataModel.customColorPicker.getExtraColor());
                }
                if (dataModel.fillCheckBox.isSelected()) {
                    dataModel.gcTemp.fillRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    dataModel.gcTemp.strokeRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            case "oval" -> {
                isShapeSelected = true;
                dataModel.gcTemp.clearRect(0 , 0, dataModel.tempCanvas.getWidth(), dataModel.tempCanvas.getHeight());
                lastX = e.getX();
                lastY = e.getY();
                try {
                    dataModel.gcTemp.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcTemp.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcTemp.setStroke(dataModel.customColorPicker.getMainColor());
                    dataModel.gcTemp.setFill(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcTemp.setStroke(dataModel.customColorPicker.getExtraColor());
                    dataModel.gcTemp.setFill(dataModel.customColorPicker.getExtraColor());
                }
                if (dataModel.fillCheckBox.isSelected()) {
                    dataModel.gcTemp.fillOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    dataModel.gcTemp.strokeOval(Math.min(startX, lastX), Math.min(startY, lastY),
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
                dataModel.gcTemp.clearRect(0, 0, dataModel.tempCanvas.getWidth(), dataModel.tempCanvas.getHeight());
                try {
                    dataModel.gcDraw.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                    dataModel.gcDraw.setFill(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                    dataModel.gcDraw.setFill(dataModel.customColorPicker.getExtraColor());
                }
                if (dataModel.fillCheckBox.isSelected()) {
                    dataModel.gcDraw.fillRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    dataModel.gcDraw.strokeRect(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            case "oval" -> {
                dataModel.gcTemp.clearRect(0, 0, dataModel.tempCanvas.getWidth(), dataModel.tempCanvas.getHeight());
                try {
                    dataModel.gcDraw.setLineWidth(Integer.parseInt(dataModel.sizeOfBrush.getText()));
                } catch (Exception exc) {
                    dataModel.gcDraw.setLineWidth(3);
                }
                if (e.getButton() == MouseButton.PRIMARY) {
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getMainColor());
                    dataModel.gcDraw.setFill(dataModel.customColorPicker.getMainColor());
                } else if (e.getButton() == MouseButton.SECONDARY){
                    dataModel.gcDraw.setStroke(dataModel.customColorPicker.getExtraColor());
                    dataModel.gcDraw.setFill(dataModel.customColorPicker.getExtraColor());
                }
                if (dataModel.fillCheckBox.isSelected()) {
                    dataModel.gcDraw.fillOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                } else {
                    dataModel.gcDraw.strokeOval(Math.min(startX, lastX), Math.min(startY, lastY),
                            Math.abs(lastX - startX), Math.abs(lastY - startY));
                }
            }
            //TODO
            default -> {}
        }
    }
}
