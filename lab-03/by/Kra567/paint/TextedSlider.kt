package by.Kra567.paint

import javafx.event.EventType
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javax.swing.event.ChangeListener
import kotlin.reflect.typeOf


class TextedSlider(start: Double,end : Double, value: Double, signs : Int,name : String) : HBox() {
    private var slider = Slider(start,end,value)
    private var valueLabel = Label("%.${signs}f".format(value) )
    init{
       // orientation = Orientation.HORIZONTAL
        children.add(Label(name))
        children.add(slider)
        children.add(valueLabel)
        onChange { valueLabel.text = "%.${signs}f".format(it) }
    }
    fun onChange(func : (Double) -> Unit) {

        slider.valueProperty().addListener(javafx.beans.value.ChangeListener { _, _, c -> func(c.toDouble()) })
    }

    fun getValue() : Double {
        return slider.value
    }


}