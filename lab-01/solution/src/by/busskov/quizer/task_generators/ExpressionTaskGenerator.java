package by.busskov.quizer.task_generators;

import by.busskov.quizer.Operation;
import by.busskov.quizer.TaskGenerator;
import by.busskov.quizer.tasks.ExpressionTask;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionTaskGenerator implements TaskGenerator {
    public ExpressionTaskGenerator(
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
    public ExpressionTask generate() {
        Random random = new Random();
        int firstNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int secondNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        if (availableOperations.isEmpty()) {
            throw new IllegalArgumentException("Cannot generate with empty EnumSet");
        }
        Object[] operations = availableOperations.toArray();
        int randomIndex = random.nextInt(operations.length);
        Operation operation = (Operation) operations[randomIndex];
        String condition = Integer.toString(firstNumber)
                + switch (operation) {
            case SUM -> '+';
            case DIFFERENCE -> '-';
            case MULTIPLICATION -> '*';
            case DIVISION -> '/';
        } + secondNumber + "=?";
        double answer = switch (operation) {
            case SUM -> firstNumber + secondNumber;
            case DIFFERENCE -> firstNumber - secondNumber;
            case MULTIPLICATION -> firstNumber * secondNumber;
            case DIVISION -> {
                if (secondNumber == 0) {
                    throw new IllegalStateException("Division by 0");
                }
                yield (double) firstNumber / secondNumber;
            }
            default -> throw new IllegalArgumentException("Invalid operation!");
        };
        return new ExpressionTask(condition, answer);
    }

    private final int minNumber;
    private final int maxNumber;
    private final EnumSet<Operation> availableOperations;

}
