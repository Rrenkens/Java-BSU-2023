package by.Dzenia.quizer.tasks.math_tasks;
import by.Dzenia.quizer.Operation;
import by.Dzenia.quizer.Result;

import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {
    protected String taskText;
    protected double answer;
    protected double precision;
    protected boolean answerIsPossibleBeZero = true;
    public String getText() {
        return taskText;
    }

    public Result validate(String answer) {
        double answerDouble;
        try {
            answerDouble = Double.parseDouble(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
        if (answerDouble == 0 && !answerIsPossibleBeZero) {
            return Result.WRONG;
        }
        if (Double.isNaN(this.answer)) {
            return Result.OK;
        }
        if (Math.abs(answerDouble - this.answer) <= Math.pow(10, -precision)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    public abstract static class Generator implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;
        protected int precision;
        protected EnumSet<Operation> includedOperations;

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> includedOperations
        ) {
            if (precision < 0) {
                throw new IllegalArgumentException("Precision cannot be negative");
            }
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("MIN_NUMBER cannot be greater tha MAX_NUMBER");
            }
            if (includedOperations.isEmpty()) {
                return;
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            this.includedOperations = includedOperations;
        }
        public static double truncateDouble(double number, int precision) {
            double multiplier = Math.pow(10, precision);
            return Math.floor(number * multiplier) / multiplier;
        }

        public double generateDouble() {
            Random random = new Random();
            return minNumber + (maxNumber - minNumber) * random.nextDouble();
        }

        public static int generatePositiveInt() {
            Random random = new Random();
            return random.nextInt(0, Integer.MAX_VALUE);
        }
        @Override
        public double getMinNumber() {
            return minNumber;
        }
        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
    }
}
