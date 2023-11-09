package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected final double eps = 1e-6;
    protected double div = 1000;
    private String text;
    protected double result;
    AbstractMathTask(String text, double result) {
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
        return Math.abs(Math.round(result * div) / div - ans) < eps ? Result.OK : Result.WRONG ;
    }
}
