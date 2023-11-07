package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    public AbstractMathTask(
            String condition,
            double answer,
            double precision
    ) {
        this.condition = condition;
        this.answer = answer;
        this.precision = precision;
    }

    @Override
    public String getText() {
        return condition;
    }

    @Override
    public Result validate(String userAnswer) {
        double userDoubleAnswer;
        try {
            userDoubleAnswer = Double.parseDouble(userAnswer);
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
        if (Math.abs(userDoubleAnswer - answer) < precision) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    protected final String condition;
    protected final double answer;
    public double precision;
}
