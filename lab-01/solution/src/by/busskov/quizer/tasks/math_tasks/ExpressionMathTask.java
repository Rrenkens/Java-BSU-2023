package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Result;

public class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(
            String condition,
            double answer,
            double precision
    ) {
        super(condition, answer, precision);
    }
}
