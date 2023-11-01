package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

import java.util.Objects;

public class EquationTask extends AbstractMathTask {

    public EquationTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

}