package by.lenson423.paint

import javafx.beans.value.ObservableValue
import javafx.embed.swing.SwingFXUtils
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.function.BiFunction
import javax.imageio.ImageIO
import kotlin.math.*

class Controller {

    companion object {
        private var instance: Controller? = null

        fun getInstance(): Controller {
            if (instance == null) {
                synchronized(Controller::class.java) {
                    if (instance == null) {
                        instance = Controller()
                    }
                }
            }
            return instance!!
        }
    }

    @FXML
    lateinit var comboBox: ComboBox<RadioButtonWithBiFunction<*, *>>

    @FXML
    lateinit var canvas: Canvas

    @FXML
    lateinit var secondCanvas: Canvas

    @FXML
    lateinit var brushButton: RadioButtonWithBiFunction<Double, Double>

    @FXML
    lateinit var eraserButton: RadioButtonWithBiFunction<Double, Double>

    @FXML
    lateinit var rectangleTool: RadioButtonWithBiFunction<MouseEvent, GraphicsContext>

    @FXML
    lateinit var circleTool: RadioButtonWithBiFunction<MouseEvent, GraphicsContext>

    @FXML
    lateinit var triangleTool: RadioButtonWithBiFunction<MouseEvent, GraphicsContext>

    @FXML
    lateinit var heartTool: RadioButtonWithBiFunction<MouseEvent, GraphicsContext>

    @FXML
    lateinit var fillColorCheckBox: CheckBox

    @FXML
    lateinit var sizeSlider: Slider

    @FXML
    lateinit var colorPicker: ColorPicker

    @FXML
    lateinit var clearCanvasButton: Button

    @FXML
    lateinit var saveImageButton: Button

    @FXML
    lateinit var openImageButton: Button

    private val radioButtons = ArrayList<RadioButtonWithBiFunction<*, *>?>()
    private val fileChooser = FileChooser()
    private var prevMouseX = 0.0
    private var prevMouseY = 0.0
    private var lastX = 0.0
    private var lastY = 0.0
    private var brushWidth = 5.0
    private var selectedColor = Color.BLACK

    fun initialize() {
        comboBox.setCellFactory { IconTextCell() }
        comboBox.buttonCell = IconTextCell()

        canvas.toFront()
        clearCanvas()
        processRadioButtons()
        sizeSlider.valueProperty().addListener { _: ObservableValue<out Number>?, _: Number?, newValue: Number
            ->
            brushWidth = newValue.toDouble()
        }
        colorPicker.valueProperty().set(Color.BLACK)
        colorPicker.valueProperty().addListener { _: ObservableValue<out Color>?, _: Color?, newValue: Color
            ->
            selectedColor = newValue
        }
        clearCanvasButton.onAction = EventHandler { clearCanvas() }
        saveImageButton.onAction = EventHandler {
            try {
                saveAsImage()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        openImageButton.onAction = EventHandler {
            try {
                openImage()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }

    private fun processRadioButtons() {
        val group = ToggleGroup()
        addRadioButtonToGroup(group, eraserButton, "eraser.png") { newX: Double, newY: Double -> drawLineOrErase(newX, newY) }
        addRadioButtonToGroup(group, brushButton, "brush.png") { newX: Double, newY: Double -> drawLineOrErase(newX, newY) }
        addRadioButtonToGroup(group, triangleTool, "triangle.png") { e: MouseEvent, gc: GraphicsContext -> drawTriangle(e, gc) }
        addRadioButtonToGroup(group, circleTool, "circle.png") { e: MouseEvent, gc: GraphicsContext -> drawCircle(e, gc) }
        addRadioButtonToGroup(group, rectangleTool, "rectangle.png") { e: MouseEvent, gc: GraphicsContext -> drawRect(e, gc) }
        addRadioButtonToGroup(group, heartTool, "heart.png") { e: MouseEvent, gc: GraphicsContext -> drawHeart(e, gc) }
        brushButton.isSelected = true
    }

    private fun <U, V> addRadioButtonToGroup(group: ToggleGroup, button: RadioButtonWithBiFunction<U, V>, path: String,
                                             func: BiFunction<U, V, Void?>) {
        button.toggleGroup = group
        button.styleClass.remove("radio-button")
        button.styleClass.add("toggle-button")
        button.graphic = ImageView(Image(javaClass.getResource(path)?.toString()))
        button.function = func
        button.setOnAction { linkingRadioButtonsWithComboBox(it) }
        radioButtons.add(button)
    }

    private fun openImage() {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"))
        val file = fileChooser.showOpenDialog(null)
        if (file != null) {
            val fileInputStream = FileInputStream(file)
            val image: Image = SwingFXUtils.toFXImage(ImageIO.read(fileInputStream), null)
            clearCanvas()
            canvas.graphicsContext2D.drawImage(image, 0.0, 0.0)
            fileInputStream.close()
        }
    }

    private fun saveAsImage() {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"))
        val file = fileChooser.showSaveDialog(null)
        if (file != null) {
            val fileOutputStream = FileOutputStream(file)
            val image: Image = canvas.snapshot(null, null)
            ImageIO.write(Objects.requireNonNull(SwingFXUtils.fromFXImage(image, null)), "png",
                    fileOutputStream)
            fileOutputStream.close()
        }
    }

    private fun clearCanvas() {
        val gcCanvas = canvas.graphicsContext2D
        gcCanvas.fill = Color.WHITE
        gcCanvas.fillRect(0.0, 0.0, canvas.width, canvas.height)
        val gcSecondLayer = secondCanvas.graphicsContext2D
        gcSecondLayer.fill = Color.TRANSPARENT
        gcSecondLayer.fillRect(0.0, 0.0, secondCanvas.width, secondCanvas.height)
    }

    private fun drawRect(e: MouseEvent, gc: GraphicsContext): Void? {
        val width = abs(prevMouseX - e.x)
        val height = abs(prevMouseY - e.y)
        if (!fillColorCheckBox.isSelected) {
            gc.strokeRect(min(e.x, prevMouseX), min(e.y, prevMouseY), width, height)
        } else {
            gc.fillRect(min(e.x, prevMouseX), min(e.y, prevMouseY), width, height)
        }
        return null
    }

    private fun drawCircle(e: MouseEvent, gc: GraphicsContext): Void? {
        val radius = sqrt((prevMouseX - e.x).pow(2.0) + (prevMouseY - e.y).pow(2.0))
        val centerX = (prevMouseX + e.x) / 2.0
        val centerY = (prevMouseY + e.y) / 2.0
        if (!fillColorCheckBox.isSelected) {
            gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2)
        } else {
            gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2)
        }
        return null
    }

    private fun drawTriangle(e: MouseEvent, gc: GraphicsContext): Void? {
        gc.beginPath()
        gc.moveTo(prevMouseX, prevMouseY)
        gc.lineTo(e.x, e.y)
        gc.lineTo(prevMouseX * 2 - e.x, e.y)
        gc.closePath()
        if (fillColorCheckBox.isSelected) {
            gc.fill()
        } else {
            gc.stroke()
        }
        return null
    }

    private fun drawHeart(e: MouseEvent, gc: GraphicsContext): Void? {
        val xMax = min(e.x, prevMouseX)
        val xMin = max(e.x, prevMouseX)
        val yMax = min(e.y, prevMouseY)
        val yMin = max(e.y, prevMouseY)
        val deltaX = xMax - xMin
        val deltaY = yMax - yMin
        gc.beginPath()
        gc.moveTo(xMin + 0.5 * deltaX, yMin)
        gc.bezierCurveTo(xMin, yMin + 0.65 * deltaY, xMin + 0.25 * deltaX, yMax,
                xMin + 0.5 * deltaX, yMin + 0.75 * deltaY)
        gc.bezierCurveTo(xMin + 0.75 * deltaX, yMax, xMax, yMin + 0.65 * deltaY, xMin + 0.5 * deltaX, yMin)
        gc.closePath()
        if (fillColorCheckBox.isSelected) {
            gc.fill()
        } else {
            gc.stroke()
        }
        return null
    }

    @FXML
    private fun startDrawing(event: MouseEvent) {
        prevMouseX = event.x
        prevMouseY = event.y
        lastX = event.x
        lastY = event.y
    }

    @FXML
    private fun drawing(event: MouseEvent) {
        val newX = event.x
        val newY = event.y
        if (brushButton.isSelected || eraserButton.isSelected) {
            drawLineOrErase(newX, newY)
        } else {
            val gc = secondCanvas.graphicsContext2D
            gc.clearRect(0.0, 0.0, secondCanvas.width, secondCanvas.height)
            drawShape(event, gc, secondCanvas)
        }
        lastX = event.x
        lastY = event.y
    }

    private fun drawShape(event: MouseEvent, gc: GraphicsContext, secondCanvas: Canvas?) {
        gc.stroke = selectedColor
        gc.fill = selectedColor
        gc.lineWidth = brushWidth
        for (button in radioButtons) {
            if (button!!.isSelected) {
                button.function?.apply(event, gc)
            }
        }
        secondCanvas!!.toFront()
    }

    private fun drawLineOrErase(newX: Double, newY: Double): Void? {
        val gc = canvas.graphicsContext2D
        gc.stroke = if (eraserButton.isSelected) Color.WHITE else selectedColor
        gc.lineWidth = brushWidth
        gc.strokeLine(lastX, lastY, newX, newY)
        return null
    }

    @FXML
    private fun endDrawing(event: MouseEvent) {
        val newX = event.x
        val newY = event.y
        if (brushButton.isSelected || eraserButton.isSelected) {
            drawLineOrErase(newX, newY)
        } else {
            val gcSecondLayer = secondCanvas.graphicsContext2D
            gcSecondLayer.clearRect(0.0, 0.0, secondCanvas.width, secondCanvas.height)
            val gcCanvas = canvas.graphicsContext2D
            drawShape(event, gcCanvas, canvas)
        }
    }

    @FXML
    fun comboBoxChanged() {
        val selectedRadioButton = comboBox.value
        selectedRadioButton?.isSelected = true
    }

    private fun linkingRadioButtonsWithComboBox(event: ActionEvent) {
        val clickedButton = event.source as RadioButtonWithBiFunction<*, *>
        if (!comboBox.items.contains(clickedButton)) {
            comboBox.selectionModel.clearSelection()
            comboBox.value = null
        } else{
            println(1)
        }
    }

}