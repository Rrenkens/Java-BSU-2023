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
import java.awt.Stroke
import java.util.LinkedList
import kotlin.math.min
import kotlin.math.max
import kotlin.math.abs

class PaintApp : Application() {
    private val canvas1: Canvas = Canvas(800.0, 600.0)
    private val graphicsContext1: GraphicsContext = canvas1.graphicsContext2D
    private val canvas2: Canvas = Canvas(800.0, 600.0)
    private val graphicsContext2: GraphicsContext = canvas2.graphicsContext2D
    private val toolBar: ToolBar = ToolBar()
    private val menuBar: MenuBar = MenuBar()
    private var paintType: PaintType = PaintType.CURVED_LINE
    private var fillColor: Color = Color.BLACK

    override fun start(primaryStage: Stage) {
        configureMenuBar()
        configureToolBar()
        graphicsContext1.fill = Color.WHITE
        graphicsContext1.fillRect(0.0, 0.0, canvas1.width, canvas1.height)
        graphicsContext1.fill = Color.BLACK
        graphicsContext1.lineCap = StrokeLineCap.ROUND
        graphicsContext1.lineWidth = 2.0
        graphicsContext2.lineWidth = 2.0

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
            baseX = round(event.x)
            baseY = round(event.y)
        }

        canvas2.setOnMouseClicked { event ->
            if (paintType == PaintType.FILLING) {
                floodFill(event.x.toInt(), event.y.toInt())
            }
        }

        canvas2.setOnMouseDragged { event ->
            when(paintType) {
                PaintType.CURVED_LINE -> {
                    graphicsContext1.strokeLine(baseX, baseY, round(event.x), round(event.y))
                    baseX = round(event.x)
                    baseY = round(event.y)
                }
                PaintType.OVAL -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext2.strokeOval(
                        round(min(baseX, event.x)),
                        round(min(baseY, event.y)),
                        round(abs(event.x - baseX)),
                        round(abs(event.y - baseY)))
                }
                PaintType.RECTANGLE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext2.strokeRect(
                        round(min(baseX, event.x)),
                        round(min(baseY, event.y)),
                        round(abs(event.x - baseX)),
                        round(abs(event.y - baseY)))
                }
                PaintType.STRAIGHT_LINE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext2.strokeLine(
                        round(baseX),
                        round(baseY),
                        round(event.x),
                        round(event.y))
                }
                else -> {}
            }
        }

        canvas2.setOnMouseReleased { event ->
            when(paintType) {
                PaintType.OVAL -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext1.strokeOval(
                        round(min(baseX, event.x)),
                        round(min(baseY, event.y)),
                        round(abs(event.x - baseX)),
                        round(abs(event.y - baseY)))
                }
                PaintType.RECTANGLE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext1.strokeRect(
                        round(min(baseX, event.x)),
                        round(min(baseY, event.y)),
                        round(abs(event.x - baseX)),
                        round(abs(event.y - baseY)))
                }
                PaintType.STRAIGHT_LINE -> {
                    graphicsContext2.clearRect(0.0, 0.0, canvas2.width, canvas2.height)
                    graphicsContext1.lineCap = StrokeLineCap.SQUARE
                    graphicsContext1.strokeLine(
                        round(baseX),
                        round(baseY),
                        round(event.x),
                        round(event.y))
                    graphicsContext1.lineCap = StrokeLineCap.ROUND
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

        val straightLine = Button()
        straightLine.graphic = ImageView(Image("file:src/main/resources/straight_line.png"))
        straightLine.setOnAction {
            paintType = PaintType.STRAIGHT_LINE
        }

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
            fillColor = colorPicker.value
        }

        val sizeSlider = Slider(1.0, 100.0, 2.0)
        val sizeLabel = Label("2")
        sizeSlider.valueProperty().addListener { _: ObservableValue<out Number>, _: Number, _: Number ->
            graphicsContext1.lineWidth = round(sizeSlider.value)
            graphicsContext2.lineWidth = round(graphicsContext1.lineWidth)
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

    private fun floodFill(x: Int, y: Int) {
        val points = Array(canvas1.width.toInt()) {Array(canvas1.height.toInt()){false}}
        val pixelReader = canvas1.snapshot(null, null).pixelReader
        val color = pixelReader.getColor(x, y)
        val pointsQueue = LinkedList<Pair<Int, Int>>()
        pointsQueue.add(x to y)

        while(!pointsQueue.isEmpty()) {
            val (curX, curY) = pointsQueue.pollLast()
            if (curX < 0 || curX >= points.size || curY < 0 || curY >= points[0].size) {
                continue
            }
            if (
                pixelReader.getColor(curX, curY) == color
                && !points[curX][curY]) {
                points[curX][curY] = true
                pointsQueue.add(curX to (curY - 1))
                pointsQueue.add(curX to (curY + 1))
                pointsQueue.add((curX - 1) to curY)
                pointsQueue.add((curX + 1) to curY)
            }
        }

        val writer = graphicsContext1.pixelWriter
        for (i in points.indices) {
            for (j in points[0].indices) {
                if (points[i][j]) {
                    writer.setColor(i, j, fillColor)
                }
            }
        }
    }

    private fun round(x: Double) : Double {
        return x.toInt().toDouble()
    }
}

fun main() {
    Application.launch(PaintApp::class.java)
}