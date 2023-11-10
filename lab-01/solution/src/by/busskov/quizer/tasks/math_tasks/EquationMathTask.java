package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.exceptions.InvalidConditionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> availableOperations,
                int precision
        ) {
            super(minNumber, maxNumber, availableOperations, precision);
            if (Math.abs(minNumber) < Math.pow(10, -precision)
            && Math.abs(maxNumber) < Math.pow(10, -precision)
            && !availableOperations.contains(Operation.SUM)
            && !availableOperations.contains(Operation.DIFFERENCE)) {
                throw new IllegalArgumentException("Not possible to generate a valid task");
            }
        }

        @Override
        public EquationMathTask generate() {
            Random random = new Random();

            double rawNumber = random.nextDouble() * getDiffNumber() + minNumber;
            double rawResult = random.nextDouble() * getDiffNumber() + minNumber;

            BigDecimal number = new BigDecimal(rawNumber).setScale(precision, RoundingMode.DOWN);
            BigDecimal result = new BigDecimal(rawResult).setScale(precision, RoundingMode.DOWN);

            Object[] operations = availableOperations.toArray();
            int randomIndex = random.nextInt(operations.length);
            Operation operation = (Operation) operations[randomIndex];
            char operationChar = switch (operation) {
                case SUM -> '+';
                case DIFFERENCE -> '-';
                case MULTIPLICATION -> '*';
                case DIVISION -> '/';
            };

            int xPosition = random.nextInt(2);
            String condition;

            if (xPosition == 0) {
                condition = "x"
                        + operationChar
                        + number.toString();
            } else {
                condition = number.toString()
                        + operationChar
                        + "x";
            }
            condition += '=' + result.toString();

            double answer = getAnswer(number, result, xPosition, operation);
            return new EquationMathTask(condition, answer, Math.pow(10, -precision) / 2);
        }
    }

    private static double getAnswer(BigDecimal bigNumber, BigDecimal bigResult, int xPosition, Operation operation) {
        double number = bigNumber.doubleValue();
        double result = bigResult.doubleValue();
        return switch (operation) {
            case DIFFERENCE -> {
                if (xPosition == 0) {
                    yield result + number;
                } else {
                    yield number - result;
                }
            }
            case MULTIPLICATION -> {
                if (number == 0) {
                    throw new InvalidConditionException(
                            "Number in equation with multiplication can't be 0"
                    );
                }
                yield (double) result / number;
            }
            case DIVISION -> {
                if (xPosition == 0) {
                    if (number == 0) {
                        throw new InvalidConditionException("Division by 0");
                    }
                    yield result * number;
                } else {
                    if (number == 0 || result == 0) {
                        throw new InvalidConditionException(
                                "Number and result can't be 0 in equation with division"
                        );
                    }
                    yield (double) number / result;
                }
            }
            case SUM -> result - number;
        };
    }
    public EquationMathTask(
            String condition,
            double answer,
            double precision
    ) {
        super(condition, answer, precision);
    }
}
