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
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.shape.StrokeLineCap
import kotlin.math.min
import kotlin.math.abs

class PaintApp : Application() {
    private val canvas1: Canvas = Canvas(800.0, 600.0)
    private val graphicsContext1: GraphicsContext = canvas1.graphicsContext2D
    private val canvas2: Canvas = Canvas(800.0, 600.0)
    private val graphicsContext2: GraphicsContext = canvas2.graphicsContext2D
    private val toolBar: ToolBar = ToolBar()
    private val menuBar: MenuBar = MenuBar()
    private var paintType: PaintType = PaintType.CURVED_LINE

    override fun start(primaryStage: Stage) {
        configureMenuBar()
        configureToolBar()
        graphicsContext1.lineCap = StrokeLineCap.ROUND

        val gridPane = GridPane()
        gridPane.add(menuBar, 0, 0)
        gridPane.add(toolBar, 0, 1)
        gridPane.add(canvas1, 0, 2)
        gridPane.add(canvas2, 0, 2)

        val scene = Scene(gridPane)
        primaryStage.title = "Paint"
        primaryStage.scene = scene
        primaryStage.show()

        var baseX = 0.0
        var baseY = 0.0
        canvas2.setOnMousePressed { event ->
            baseX = event.x
            baseY = event.y
        }

        canvas2.setOnMouseDragged { event ->
            when(paintType) {
                PaintType.CURVED_LINE -> {
                    graphicsContext1.strokeLine(baseX, baseY, event.x, event.y)
                    baseX = event.x
                    baseY = event.y
                }
                PaintType.OVAL -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext2.strokeOval(
                        min(baseX, event.x),
                        min(baseY, event.y),
                        abs(event.x - baseX),
                        abs(event.y - baseY))
                }
                PaintType.RECTANGLE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext2.strokeRect(
                        min(baseX, event.x),
                        min(baseY, event.y),
                        abs(event.x - baseX),
                        abs(event.y - baseY))
                }
                else -> {}
            }
        }

        canvas2.setOnMouseReleased { event ->
            when(paintType) {
                PaintType.OVAL -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext1.strokeOval(
                        min(baseX, event.x),
                        min(baseY, event.y),
                        abs(event.x - baseX),
                        abs(event.y - baseY))
                }
                PaintType.RECTANGLE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext1.strokeRect(
                        min(baseX, event.x),
                        min(baseY, event.y),
                        abs(event.x - baseX),
                        abs(event.y - baseY))
                }
                else -> {}
            }
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

        val filling = Button()
        filling.graphic = ImageView(Image("file:src/main/resources/filling.png"))
        filling.setOnAction {
            paintType = PaintType.FILLING
        }

        val colorPicker = ColorPicker(Color.BLACK)
        colorPicker.setOnAction {
            graphicsContext1.stroke = colorPicker.value
            graphicsContext1.fill = colorPicker.value
            graphicsContext2.stroke = colorPicker.value
            graphicsContext2.fill = colorPicker.value
        }

        val sizeSlider = Slider(1.0, 100.0, 5.0)
        val sizeLabel = Label("5")
        sizeSlider.valueProperty().addListener { _: ObservableValue<out Number>, _: Number, newValue: Number ->
            graphicsContext1.lineWidth = sizeSlider.value
            graphicsContext2.lineWidth = graphicsContext1.lineWidth
            sizeLabel.text = graphicsContext1.lineWidth.toInt().toString();
        }

        toolBar.items.addAll(
            curvedLine,
            straightLine,
            oval,
            rectangle,
            filling,
            colorPicker,
            sizeSlider,
            sizeLabel)
    }
}

fun main() {
    Application.launch(PaintApp::class.java)
}