package by.busskov.quizer.tasks;

import by.busskov.quizer.Result;
import by.busskov.quizer.Task;

public class ExpressionTask implements Task {
    public ExpressionTask(
            String condition,
            double answer
    ) {
        this.condition = condition;
        this.answer = answer;
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
        if (Math.abs(userDoubleAnswer - answer) < PRECISION) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    private final String condition;
    private final double answer;

    public static double PRECISION = 1e-3;

}
