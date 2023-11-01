package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

import java.util.Objects;

import static java.lang.Math.round;

public class ExpressionTask extends AbstractMathTask {

    public ExpressionTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }
}

