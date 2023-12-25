package by.Kra567.paint

import javafx.embed.swing.SwingFXUtils
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.awt.event.ActionEvent
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO
import kotlin.math.max

class Loader(private var stage : Stage,private var canvas : ActedCanvas) : Button("load") {
    init{
        maxWidth = Double.POSITIVE_INFINITY
        onAction = EventHandler { load() }
    }
    private fun load(){
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("image formats","*.jpg","*.png"))
        val file : File? = fileChooser.showOpenDialog(null)
        file?.let{
            stage.isResizable = true
            val fileInputStream = FileInputStream(file)
            val image: Image = SwingFXUtils.toFXImage(ImageIO.read(fileInputStream), null)






            fileInputStream.close()
            stage.width = stage.width - canvas.width + image.width
            stage.height = max(image.height,stage.height)
            canvas.width = image.width
            canvas.height = image.height
            canvas.graphicsContext2D.drawImage(image, 0.0, 0.0)
            stage.isResizable = false
        }

    }
}