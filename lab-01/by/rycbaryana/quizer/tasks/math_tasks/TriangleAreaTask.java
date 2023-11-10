package by.rycbaryana.quizer.tasks.math_tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;

import java.text.DecimalFormat;
import java.util.EnumSet;

public class TriangleAreaTask extends AbstractMathTask {
    double a;
    double b;
    int precision;

    TriangleAreaTask(double a, double b, int precision) {
        this.a = a;
        this.b = b;
        this.precision = precision;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double min, double max) {
            super(EnumSet.noneOf(Operation.class), min, max);
        }

        public Generator(double min, double max, int precision) {
            super(EnumSet.noneOf(Operation.class), min, max, precision);
        }

        @Override
        public TriangleAreaTask generate() {
            double a = generateNumber();
            double b = generateNumber();
            return new TriangleAreaTask(a, b, precision);
        }
    }

    @Override
    public String getText() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        return String.format("What is an area of right triangle with cathets %s, %s", df.format(a), df.format(b));
    }

    @Override
    public Result validate(Answer answer) {
        if (!answer.isNumeric()) {
            return Result.INCORRECT_INPUT;
        }

        double eps = 1e-8;
        if (Math.abs(a * b / 2 - answer.getNum()) < eps) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
