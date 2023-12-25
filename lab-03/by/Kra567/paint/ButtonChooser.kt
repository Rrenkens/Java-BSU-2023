package by.Kra567.paint

import javafx.event.EventType
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.Toggle
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.*
import java.awt.Color

class ButtonChooser(initial : Int, vararg options : String): VBox() {
    private var toggleGroup = ToggleGroup()
    private var buttons = options.map{ToggleButton(it)}

    private fun getIndex(button: Toggle) : Int{
        for (i in (0 until buttons.size)){
            if (buttons.get(i) == button){
                return i
            }
        }
        throw error("No such button")
    }
    fun onChange(func : (Int) -> Unit) {
        toggleGroup.selectedToggleProperty().addListener { _, oldToggle, newToggle ->
            newToggle?.let { func(getIndex(it)) } ?: (oldToggle?.let { toggleGroup.selectToggle(it) })
        }

    }


    init{
        for (btn in buttons){
            btn.maxWidth = Double.POSITIVE_INFINITY
        }
        toggleGroup.toggles.addAll(buttons)
        children.addAll(buttons)
        toggleGroup.selectToggle(buttons.get(initial))

    }
}