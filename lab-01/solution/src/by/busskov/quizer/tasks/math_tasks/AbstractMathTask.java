package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Result;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    public static abstract class Generator implements MathTask.Generator {
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> availableOperations,
                int precision
        ) {
            if (minNumber > maxNumber) {
                throw new IllegalArgumentException("Min number is greater than max number");
            }
            if (availableOperations.isEmpty()) {
                throw new IllegalArgumentException("There is no available operations for generator");
            }
            if (precision < 0) {
                throw new IllegalArgumentException("Precision can't be positive");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.availableOperations = EnumSet.copyOf(availableOperations);
            this.precision = precision;
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        protected final double minNumber;
        protected final double maxNumber;
        protected final EnumSet<Operation> availableOperations;
        protected final int precision;
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
