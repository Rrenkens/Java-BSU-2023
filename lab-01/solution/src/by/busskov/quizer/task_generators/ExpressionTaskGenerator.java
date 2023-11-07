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
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.availableOperations = EnumSet.copyOf(availableOperations);
    }
    @Override
    public ExpressionTask generate() {
        Random random = new Random();
        int firstNumber = random.nextInt(maxNumber - minNumber) + minNumber;
        int secondNumber = random.nextInt(maxNumber - minNumber) + minNumber;

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
            case DIVISION -> (double) firstNumber / secondNumber;
        };
        return new ExpressionTask(condition, answer);
    }

    private final int minNumber;
    private final int maxNumber;
    private final EnumSet<Operation> availableOperations;

}
