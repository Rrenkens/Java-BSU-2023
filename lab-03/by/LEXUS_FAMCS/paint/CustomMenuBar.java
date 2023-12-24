package by.LEXUS_FAMCS.paint;

import javafx.application.Platform;
import javafx.scene.control.*;

import java.io.File;

public class CustomMenuBar extends MenuBar {
    private final DataModel dataModel;
    private final Menu file;
    private final MenuItem open;
    private final MenuItem save;
    private final MenuItem saveAs;
    private final MenuItem exit;

    CustomMenuBar(DataModel dataModel) {
        this.dataModel = dataModel;
        file = new Menu("File");
        open = new MenuItem("Open...");
        save = new MenuItem("Save...");
        saveAs = new MenuItem("Save as...");
        exit = new MenuItem("Exit");
        file.getItems().addAll(open, new SeparatorMenuItem(), save, saveAs, new SeparatorMenuItem(), exit);
        createSetOnActionEvents();

        getMenus().addAll(file);
    }

    private void createSetOnActionEvents() {
        open.setOnAction(e -> {
            File file = dataModel.loader.load();
            if (file != null) {
                dataModel.toFile = file;
                dataModel.stage.setTitle(file.getName() + " - Pinta");
            }
        });

        saveAs.setOnAction(e -> {
            dataModel.toFile = dataModel.saver.save();
            if (dataModel.toFile != null) {
                dataModel.stage.setTitle(dataModel.toFile.getName() + " - Pinta");
            }
        });

        save.setOnAction(e -> {
            try {
                if (dataModel.toFile != null) {
                    dataModel.saver.saveIntoFile(dataModel.toFile);
                } else {
                    dataModel.toFile = dataModel.saver.save();
                }
                dataModel.stage.setTitle(dataModel.toFile.getName() + " - Pinta");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });
    }
}
    
