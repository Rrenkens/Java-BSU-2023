package by.Kra567.paint

import javafx.embed.swing.SwingFXUtils
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.stage.FileChooser
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.imageio.ImageIO

class Saver(private val canvas : Canvas) : Button("save") {
    init {
        onAction = EventHandler { save() }
        maxWidth = Double.POSITIVE_INFINITY
    }

    fun save() : Unit {
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("image formats","*.jpg","*.png"))
        val file : File? = fileChooser.showSaveDialog(null)
        file?.let{
            val fileOutputStream = FileOutputStream(file)
            val image: Image = canvas.snapshot(null, null)
            ImageIO.write(
                    Objects.requireNonNull(SwingFXUtils.fromFXImage(image, null)), "png",
                    fileOutputStream
            )
            fileOutputStream.close()
        }
    }
}