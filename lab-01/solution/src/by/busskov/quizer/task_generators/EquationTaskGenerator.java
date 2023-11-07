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
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.availableOperations = EnumSet.copyOf(availableOperations);
    }
    @Override
    public EquationTask generate() {
        Random random = new Random();
        int number = random.nextInt(maxNumber - minNumber) + minNumber;
        int result = random.nextInt(maxNumber - minNumber) + minNumber;

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
            answer = switch (operation) {
                case SUM -> result - number;
                case DIFFERENCE -> result + number;
                case MULTIPLICATION -> (double) result / number;
                case DIVISION -> result * number;
            };
        } else {
            condition = Integer.toString(number)
                    + operationChar
                    + 'x';
            answer = switch (operation) {
                case SUM -> result - number;
                case DIFFERENCE -> number - result;
                case MULTIPLICATION -> (double) result / number;
                case DIVISION -> (double) number / result;
            };
        }
        return new EquationTask(condition, answer);
    }

    private final int minNumber;
    private final int maxNumber;
    private final EnumSet<Operation> availableOperations;

}
