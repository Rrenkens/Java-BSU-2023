package by.arteman17.quizer.tasks.math_tasks;

import by.arteman17.quizer.Result;

public class AbstractMathTask implements MathTask {
    private final String text_;
    private final double correctAns_;

    public AbstractMathTask(String text, double ans) {
        if (text == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        if (text.isEmpty()) {
            throw new IllegalArgumentException("Argument is empty");
        }

        text_ = text;
        correctAns_ = ans;
    }
    @Override
    public String getText() {
        return text_;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        try {
            double tmp = Double.parseDouble(answer);
        } catch (Exception ex) {
            return Result.INCORRECT_INPUT;
        }
        if (Math.abs(correctAns_ - Double.parseDouble(answer)) < 0.000001) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
