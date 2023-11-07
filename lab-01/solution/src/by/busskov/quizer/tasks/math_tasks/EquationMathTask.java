package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Result;

public class EquationMathTask extends AbstractMathTask {
    public EquationMathTask(
            String condition,
            double answer,
            double precision
    ) {
        super(condition, answer, precision);
    }
}
