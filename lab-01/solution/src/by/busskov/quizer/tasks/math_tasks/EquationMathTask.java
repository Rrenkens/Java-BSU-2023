package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.exceptions.InvalidConditionException;

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
            if (minNumber == 0
            && maxNumber == 0
            && !availableOperations.contains(Operation.SUM)
            && !availableOperations.contains(Operation.DIFFERENCE)) {
                throw new IllegalArgumentException("Not possible to generate a valid task");
            }
        }

        @Override
        public EquationMathTask generate() {
            Random random = new Random();
            int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int result = random.nextInt(maxNumber - minNumber + 1) + minNumber;

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
