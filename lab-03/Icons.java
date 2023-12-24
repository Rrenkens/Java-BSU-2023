import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Icons extends HBox {
    private final DataModel dataModel;
    private final Button create;
    private final Button open;
    private final Button save;
    private final Button paste;
    private final Button clear;
    Icons(DataModel dataModel) {
        this.dataModel = dataModel;
        
        setPadding(new Insets(0, 0, 0, 8));

        create = new Button();
        ImageView createImageView = new ImageView(new Image("/images/Pinta/create.png"));
        createImageView.setFitWidth(30);
        createImageView.setFitHeight(30);
        create.setStyle("-fx-background-color: transparent;");
        create.setGraphic(createImageView);
        create.setPrefSize(createImageView.getFitWidth() + 13, createImageView.getFitHeight() + 13);

        open = new Button();
        ImageView openImageView = new ImageView(new Image("/images/Pinta/open.png"));
        openImageView.setFitWidth(createImageView.getFitWidth() + 4);
        openImageView.setFitHeight(createImageView.getFitHeight() + 4);
        open.setStyle("-fx-background-color: transparent;");
        open.setGraphic(openImageView);
        open.setText("  Open");
        open.setFont(DataModel.font);

        save = new Button();
        ImageView saveImageView = new ImageView(new Image("/images/Pinta/save.png"));
        saveImageView.setFitWidth(createImageView.getFitWidth());
        saveImageView.setFitHeight(createImageView.getFitHeight());
        save.setStyle("-fx-background-color: transparent;");
        save.setGraphic(saveImageView);
        save.setText("  Save");
        save.setFont(DataModel.font);
        save.setPrefSize(100, openImageView.getFitHeight() + 10);

        paste = new Button();
        ImageView pasteImageView = new ImageView(new Image("/images/Pinta/paste.png"));
        pasteImageView.setFitWidth(createImageView.getFitWidth() + 4);
        pasteImageView.setFitHeight(createImageView.getFitHeight());
        paste.setStyle("-fx-background-color: transparent;");
        paste.setGraphic(pasteImageView);
        paste.setText("  Paste");
        paste.setFont(DataModel.font);
        paste.setPrefSize(115, openImageView.getFitHeight() + 10);

        clear = new Button();
        ImageView clearImageView = new ImageView("/images/Pinta/clear.png");
        clearImageView.setFitWidth(createImageView.getFitWidth() + 4);
        clearImageView.setFitHeight(createImageView.getFitHeight());
        clear.setStyle("-fx-background-color: transparent;");
        clear.setGraphic(clearImageView);
        clear.setText("  Clear");
        clear.setFont(DataModel.font);
        clear.setPrefSize(115, openImageView.getFitHeight() + 10);

        List<Button> buttons = List.of(create, open, save, paste, clear);
        setMouseEnterEvent(buttons);
        setMouseExitEvent(buttons);

        createSetOnActionEvents();

        getChildren().addAll(create, open, save, new Separator(Orientation.VERTICAL), paste, new Separator(Orientation.VERTICAL), clear);
    }
    private void setMouseEnterEvent(List<Button> buttons) {
        buttons.forEach(btn -> btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");
        }));
    }

    private void setMouseExitEvent(List<Button> buttons) {
        buttons.forEach(btn -> btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: transparent;");
        }));
    }
    
    private void createSetOnActionEvents() {
        create.setOnAction(e -> {
            CreateDialog createDialog = new CreateDialog();
            Optional<String> response = createDialog.showAndWait();
            response.ifPresent(answer -> {
                if (!answer.equals("Cancel")) {
                    if (answer.equals("Save")) {
                        if (dataModel.toFile != null) {
                            try {
                                dataModel.saver.saveIntoFile(dataModel.toFile);
                            } catch (IOException ex) {}
                        } else {
                            dataModel.toFile = dataModel.saver.save();
                        }
                    }
                    dataModel.gcDraw.clearRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
                    dataModel.gcDraw.setStroke(Color.BLACK);
                    dataModel.gcDraw.setLineWidth(2);
                    dataModel.gcDraw.strokeRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());

                }
            });
        });

        open.setOnAction(e -> {
            File file = dataModel.loader.load();
            if (file != null) {
                dataModel.toFile = file;
                dataModel.stage.setTitle(file.getName() + " - Pinta");
            }
        });

        save.setOnAction(e -> {
            if (dataModel.toFile != null) {
                try {
                    dataModel.saver.saveIntoFile(dataModel.toFile);
                } catch (IOException ex) {}
            } else {
                File file = dataModel.saver.save();
                if (file != null) {
                    dataModel.stage.setTitle(file.getName() + " - Pinta");
                }
            }
        });

        clear.setOnAction(e -> {
            dataModel.gcDraw.clearRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
            dataModel.gcDraw.setLineWidth(1);
            dataModel.gcDraw.setStroke(Color.BLACK);
            dataModel.gcDraw.strokeRect(0, 0, dataModel.drawCanvas.getWidth(), dataModel.drawCanvas.getHeight());
        });
    }

    class CreateDialog extends Dialog<String> {
        CreateDialog() {
            setSpacing(8);

            String title = dataModel.stage.getTitle();
            Label label1 = new Label("Do you want to save " + title.substring(0, title.lastIndexOf(' ') - 2) + " before closing?");
            label1.setFont(DataModel.font);
            Label label2 = new Label("If you don't save your changes, they will be lost forever");
            VBox text = new VBox(label1, label2);
            text.setSpacing(8);
            getDialogPane().setContent(text);

            ButtonType closeWithoutSavingButton = new ButtonType("Close without saving", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.CANCEL_CLOSE);

            getDialogPane().getButtonTypes().addAll(
                    closeWithoutSavingButton,
                    cancelButton,
                    saveButton
                    );

            setResultConverter(buttonType -> {
                if (buttonType == closeWithoutSavingButton) {
                    return "Close without saving";
                } else if (buttonType == cancelButton) {
                    return "Cancel";
                } else if (buttonType == saveButton) {
                    return "Save";
                }
                return null;
            });

        }
    }

}
