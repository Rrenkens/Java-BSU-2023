package by.lenson423.paint

import javafx.scene.control.RadioButton
import java.util.function.BiFunction

class RadioButtonWithBiFunction<out T, out U> : RadioButton() {
    var function: BiFunction<@UnsafeVariance T, @UnsafeVariance U, Void?>? = null
}
