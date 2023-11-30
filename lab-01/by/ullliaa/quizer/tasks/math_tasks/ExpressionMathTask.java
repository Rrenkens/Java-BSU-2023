package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Result;
import by.ullliaa.quizer.by.ullliaa.quizer.Task;

import java.util.Objects;

public class ExpressionMathTask extends AbstractMathTask {
    private final String text;

    public ExpressionMathTask(String text, double answer) {
        this.text = text;
        this.answer = answer;
    }

    /**
     @return текст задания
     */
    public String getText() {
        return text;
    };
}
