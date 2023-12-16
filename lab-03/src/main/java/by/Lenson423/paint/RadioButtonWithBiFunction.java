package by.lenson423.paint;

import javafx.scene.control.RadioButton;

import java.util.function.BiFunction;

public class RadioButtonWithBiFunction<T, U> extends RadioButton {
    private BiFunction<T, U, Void> function;

    void setFunction(BiFunction<T, U, Void> function){
        this.function = function;
    }

    BiFunction<T, U, Void> getFunction(){
        return function;
    }
}
