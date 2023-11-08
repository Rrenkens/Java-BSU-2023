package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Operation;
import by.busskov.quizer.Result;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    public static abstract class Generator implements MathTask.Generator {
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> availableOperations
        ) {
            if (minNumber > maxNumber) {
                throw new IllegalArgumentException("min number is greater than max number");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.availableOperations = EnumSet.copyOf(availableOperations);
        }

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }

        protected final int minNumber;
        protected final int maxNumber;
        protected final EnumSet<Operation> availableOperations;
    }

    public AbstractMathTask(
            String condition,
            double answer,
            double precision
    ) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision cannot be negative");
        }
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
