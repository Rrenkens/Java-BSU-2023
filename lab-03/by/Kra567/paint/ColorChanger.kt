package by.Kra567.paint

import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.*
import javafx.scene.paint.Color
import kotlin.math.abs

class ColorChanger : VBox() {
    //private var currentColor = Color(0.0,1.0,0.0,0.0)
    private var colorLabel = Label()
    private var rgbaSliders = listOf(
            TextedSlider(0.0,255.0,0.0,0," R"),
            TextedSlider(0.0,255.0,0.0,0," G"),
            TextedSlider(0.0,255.0,0.0,0," B"),
            TextedSlider(0.0,1.0,1.0,2," A")
    )

    fun getCurrentColor() : Color {
        return Color(rgbaSliders.get(0).getValue()/255.0,
                     rgbaSliders.get(1).getValue()/255.0,
                     rgbaSliders.get(2).getValue()/255.0,
                     rgbaSliders.get(3).getValue()
                )
    }

    private fun changeLabel(){
        val mainColor = getCurrentColor()
        colorLabel.background = Background(BackgroundFill(mainColor, CornerRadii.EMPTY, Insets.EMPTY))


    }
    init{
        colorLabel.maxWidth = Double.POSITIVE_INFINITY
        colorLabel.alignment = Pos.CENTER
        changeLabel()
        children.add(colorLabel)
        children.addAll(rgbaSliders)
        style = "-fx-border-color: black"
        for (slider in rgbaSliders){
            slider.onChange { changeLabel()}
        }

    }
}