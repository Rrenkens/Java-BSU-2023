package by.LEXUS_FAMCS.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Saver {
    private DataModel dataModel;
    private Canvas canvas;
    Saver(Canvas canvas, DataModel dataModel) {
        this.canvas = canvas;
        this.dataModel = dataModel;
    }
    public File save() {
        FileChooser fileChooser = new FileChooser();
        String title = dataModel.stage.getTitle();
        fileChooser.setInitialFileName(title.substring(0, title.lastIndexOf(' ') - 2));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.gif"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image files (*.png, *.jpeg, *.jpg, *.gif)", "*.png", "*.jpeg", "*.jpg", "*.gif")
        );

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                saveIntoFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public void saveIntoFile(File file) throws IOException {
        String extension = ""; // Получаем расширение файла
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i + 1);
        }
        Image snapshot = canvas.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null),
                extension.equals("jpeg") || extension.equals("jpg") ? "png" : extension,
                file);
    }
}