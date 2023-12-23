package by.AlexHanimar.quizer.tasks.math_tasks;

import by.AlexHanimar.quizer.Result;
import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {

    public static abstract class Generator implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;
        protected EnumSet<Operation> ops;
        protected int precision;

        protected double Round(double val, int precision) {
            return new BigDecimal(val).setScale(precision, RoundingMode.DOWN).doubleValue();
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> ops) throws IllegalArgumentException {
            if (minNumber >= maxNumber || ops.isEmpty())
                throw new IllegalArgumentException();
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            this.ops = ops;
        }

        @Override
        public abstract MathTask generate() throws TaskGenerationException;
    }

    protected String text;
    protected double answer;
    protected final double eps = 1e-9;
    @Override
    public Result validate(String answer) {
        try {
            double val = Double.parseDouble(answer);
            if (Math.abs(this.answer - val) <= eps)
                return Result.OK;
            else {
                return Result.WRONG;
            }
        } catch (NumberFormatException ex) {
            return Result.INCORRECT_INPUT;
        }
    }
}
