package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Operation;

import java.util.EnumSet;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> availableOperations
        ) {
            super(minNumber, maxNumber, availableOperations);
        }

        @Override
        public EquationMathTask generate() {
            Random random = new Random();
            int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int result = random.nextInt(maxNumber - minNumber + 1) + minNumber;

            if (availableOperations.isEmpty()) {
                throw new IllegalArgumentException("Cannot generate with empty EnumSet");
            }
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
            double answer;

            if (xPosition == 0) {
                condition = 'x'
                        + operationChar
                        + Integer.toString(number);
            } else {
                condition = Integer.toString(number)
                        + operationChar
                        + 'x';
            }
            condition += '=' + result;

            answer = switch (operation) {
                case SUM -> result - number;
                case DIFFERENCE -> {
                    if (xPosition == 0) {
                        yield result + number;
                    } else {
                        yield number - result;
                    }
                }
                case MULTIPLICATION -> {
                    if (number == 0) {
                        throw new IllegalStateException("Number in equation can't be 0");
                    }
                    yield (double) result / number;
                }
                case DIVISION -> {
                    if (xPosition == 0) {
                        if (number == 0) {
                            throw new IllegalStateException("Division by 0");
                        }
                        yield result * number;
                    } else {
                        if (number == 0 || result == 0) {
                            throw new IllegalStateException("0 in division in equation");
                        }
                        yield (double) number / result;
                    }
                }
                default -> throw new IllegalArgumentException("Invalid operation!");
            };
            return new EquationMathTask(condition, answer, 1e-3);
        }
    }
//TODO remove hardcode precision from return

    public EquationMathTask(
            String condition,
            double answer,
            double precision
    ) {
        super(condition, answer, precision);
    }
}
