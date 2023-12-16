package by.lenson423.paint

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.util.*

class Main : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(Objects.requireNonNull(javaClass.getResource("config.fxml")))
        primaryStage.title = "Paint"
        primaryStage.isResizable = false
        val scene = Scene(root, 800.0, 600.0)
        scene.stylesheets.add(Objects.requireNonNull(javaClass.getResource("styles.css")).toExternalForm())
        primaryStage.scene = scene
        primaryStage.initStyle(StageStyle.DECORATED)
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
