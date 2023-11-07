package by.busskov.quizer.task_generators;

import by.busskov.quizer.Operation;
import by.busskov.quizer.TaskGenerator;
import by.busskov.quizer.tasks.EquationTask;

import java.util.EnumSet;
import java.util.Random;

public class EquationTaskGenerator implements TaskGenerator {
    public EquationTaskGenerator(
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
    public EquationTask generate() {
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
        return new EquationTask(condition, answer);
    }

    private final int minNumber;
    private final int maxNumber;
    private final EnumSet<Operation> availableOperations;

}
