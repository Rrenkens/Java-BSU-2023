package by.lenson423.paint

import javafx.event.EventHandler
import javafx.scene.control.ContentDisplay
import javafx.scene.control.ListCell


class IconTextCell : ListCell<RadioButtonWithBiFunction<*, *>>() {

    init {
        contentDisplay = ContentDisplay.GRAPHIC_ONLY
    }

    override fun updateItem(item: RadioButtonWithBiFunction<*, *>?, empty: Boolean) {
        super.updateItem(item, empty)

        if (item == null || empty) {
            graphic = null
        } else {
            graphic = RadioButtonWithBiFunction<Any?, Any?>().apply {
                this.text = item.text
                this.graphic = item.graphic
                this.function = item.function
                this.onMouseReleased = EventHandler {
                    Controller.getInstance().comboBox.value = item
                    item.isSelected = true
                }
            }
            graphic.styleClass.remove("radio-button")
            graphic.styleClass.add("toggle-button-in-combobox")
            (graphic as RadioButtonWithBiFunction<Any?, Any?>).isSelected = item.isSelected
        }
    }
}
