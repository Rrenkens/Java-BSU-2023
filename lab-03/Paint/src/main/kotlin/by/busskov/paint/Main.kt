import by.busskov.paint.PaintType
import javafx.application.Application
import javafx.beans.value.ObservableValue
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.control.Slider
import javafx.scene.control.ToolBar
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.StrokeLineCap

class PaintApp : Application() {
    private val canvas: Canvas = Canvas(800.0, 600.0)
    private val graphicsContext: GraphicsContext = canvas.graphicsContext2D
    private val toolBar: ToolBar = ToolBar()
    private val menuBar: MenuBar = MenuBar()
    private var paintType: PaintType = PaintType.CURVED_LINE

    override fun start(primaryStage: Stage) {
        graphicsContext.lineCap = StrokeLineCap.ROUND
        configureMenuBar()
        configureToolBar()
        val vbox = VBox(menuBar, toolBar, canvas)

        val scene = Scene(vbox)
        primaryStage.title = "Paint"
        primaryStage.scene = scene
        primaryStage.show()

        var previousX = 0.0
        var previousY = 0.0
        canvas.setOnMousePressed { event ->
            previousX = event.x
            previousY = event.y
        }
        canvas.setOnMouseDragged { event ->
            
            graphicsContext.strokeLine(previousX, previousY, event.x, event.y)
            previousX = event.x
            previousY = event.y
        }
    }

    private fun configureMenuBar() {
        val fileMenu = Menu("File")
        val saveItem = MenuItem("Save")
        val openItem = MenuItem("Open")
        fileMenu.items.addAll(saveItem, openItem)
        menuBar.menus.add(fileMenu)
    }

    private fun configureToolBar() {
        val curvedLine = Button()
        curvedLine.graphic = ImageView(Image("file:src/main/resources/curved_line.png"))
        curvedLine.setOnAction {
            paintType = PaintType.CURVED_LINE
        }

        val straightLine = Button("Straight line")

        val oval = Button()
        oval.graphic = ImageView(Image("file:src/main/resources/circle.png"))
        oval.setOnAction {
            paintType = PaintType.OVAL
        }

        val rectangle = Button()
        rectangle.graphic = ImageView(Image("file:src/main/resources/rectangle.png"))
        rectangle.setOnAction {
            paintType = PaintType.RECTANGLE
        }

        val colorPicker = ColorPicker(Color.BLACK)
        colorPicker.setOnAction {
            graphicsContext.stroke = colorPicker.value
            graphicsContext.fill = colorPicker.value
        }

        val sizeSlider = Slider(1.0, 100.0, 5.0)
        val sizeLabel = Label("5")
        sizeSlider.valueProperty().addListener { _: ObservableValue<out Number>, _: Number, newValue: Number ->
            graphicsContext.lineWidth = sizeSlider.value
            sizeLabel.text = graphicsContext.lineWidth.toInt().toString();
        }

        toolBar.items.addAll(
            curvedLine,
            straightLine,
            oval,
            rectangle,
            colorPicker,
            sizeSlider,
            sizeLabel)
    }
}

fun main() {
    Application.launch(PaintApp::class.java)
}