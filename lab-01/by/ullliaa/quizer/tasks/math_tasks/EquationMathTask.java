package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import java.util.Objects;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    private final String text;

    public EquationMathTask(String text, double answer) {
        this.text = text;
        this.answer = answer;
    }

    /**
     * @return текст задания
     */
    public String getText() {
        return text;
    }

}
