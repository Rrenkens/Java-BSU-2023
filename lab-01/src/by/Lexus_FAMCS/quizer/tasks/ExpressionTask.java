package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;

import java.util.Arrays;

public class ExpressionTask implements Task {
    private final double eps = 1e-6;
    private String text;
    private double result;
    public ExpressionTask(String text, double result) {
        this.text = text;
        this.result = result;
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        double ans;
        try {
            ans = Double.parseDouble(answer);
        } catch (NumberFormatException exc) {
            return Result.INCORRECT_INPUT;
        }
        return Math.abs((double) Math.round(result * 1000) / 1000 - ans) < eps ? Result.OK : Result.WRONG ;
    }
}
