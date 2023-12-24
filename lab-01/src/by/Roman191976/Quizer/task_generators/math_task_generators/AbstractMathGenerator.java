package by.Roman191976.Quizer.task_generators.math_task_generators;
import by.Roman191976.Quizer.tasks.math_tasks.MathTask.Operation;

import java.util.Random;

public abstract class AbstractMathGenerator implements MathTaskGenerator {
    private int minNumber;
    private int maxNumber;
    private Operation[] availableOperations;
    public Random random;

    public AbstractMathGenerator(
            int minNumber,
            int maxNumber,
            Operation[] operations) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("нижняя граница больше верхней");
        }
        this.availableOperations = operations;
        this.random = new Random();
    }

    public Operation generateRandomOperator() {;
        int index = random.nextInt(availableOperations.length);
        return availableOperations[index];
    }

    public int generateRandomNumber() {
        return random.nextInt(maxNumber - minNumber + 1) + minNumber;
    }

    public int generateRandomNumberExceptZero() {
        int number = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        return number == 0 ? 1 : number;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
}