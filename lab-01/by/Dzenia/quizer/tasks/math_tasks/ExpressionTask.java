package by.Dzenia.quizer.tasks.math_tasks;

import by.Dzenia.quizer.Operation;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(double value1, double value2, Operation op, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException("Precision cannot be negative");
        }
        this.precision = precision;
        switch (op) {
            case SUM -> {
                taskText = Double.toString(value1) + " + " + Double.toString(value2) + " = ?";
                answer = value1 + value2;
            }
            case DIFFERENCE -> {
                taskText = Double.toString(value1) + " - " + Double.toString(value2) + " = ?";
                answer = value1 - value2;
            }
            case MULTIPLICATION -> {
                taskText = Double.toString(value1) + " * " + Double.toString(value2) + " = ?";
                answer = value1 * value2;
            }
            case DIVISION -> {
                if (value2 == 0) {
                    throw new ArithmeticException("Have a zero division");
                }
                taskText = Double.toString(value1) + " / " + Double.toString(value2) + " = ?";
                answer = value1 / value2;
            }
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> includedOperations) {
            super(minNumber, maxNumber, precision, includedOperations);
        }

        public ExpressionTask generate() {
            while (true) {
                try {
                    int randomIndex = generatePositiveInt() % includedOperations.size();
                    Operation operation = includedOperations.stream().toList().get(randomIndex);
                    double firstValue = truncateDouble(generateDouble(), precision);
                    double secondValue = truncateDouble(generateDouble(), precision);
                    return new ExpressionTask(firstValue, secondValue, operation, precision);
                } catch (Exception ignored) {

                }
            }
        }
    }
}
