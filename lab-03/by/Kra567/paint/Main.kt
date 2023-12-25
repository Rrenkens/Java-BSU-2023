import by.Kra567.paint.*
import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.FlowPane
import javafx.stage.Stage
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import java.util.concurrent.Flow


class Main : Application(), PenEnvironment{
    private var canvas = ActedCanvas(500.0,500.0,{this},LineActor(),ShapeActor(::DrawRect),ShapeActor(::DrawOval))
    private var slider = TextedSlider(1.0,100.0,1.0,0,"px")
    private var colorChanger = ColorChanger()
    private var modeSelector = ButtonChooser(0,"draw","rect","oval")


    override fun getPenColor(): Color {
        return colorChanger.getCurrentColor()
    }

    override fun getPenThickness(): Double {
        return slider.getValue()
    }



    override fun start(primaryStage: Stage?) {

        primaryStage?.isResizable = false
        primaryStage?.title = "Sex"
        //primaryStage?.width = 600.0
        //primaryStage?.height = 600.0


        slider.style = "-fx-border-color: black"
        modeSelector.style = "-fx-border-color : black"
        modeSelector.onChange { canvas.switch(it) }
        var leftPane = VBox(
                slider,
                colorChanger,
                modeSelector,
                Saver(canvas),
                primaryStage?.let{Loader(it,canvas)})
        leftPane.maxWidth = Double.POSITIVE_INFINITY
        leftPane.style = "-fx-border-color:black"
                //leftPane.minWidth = leftPane.width+100;
        //var fictivePane = FlowPane(canvas)
        var mainPane = HBox(leftPane,canvas)
        var scene = Scene(mainPane)
        primaryStage?.scene = scene
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}