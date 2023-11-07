package by.busskov.quizer.task_generators.math_task_generators;

import by.busskov.quizer.Operation;

import java.util.EnumSet;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    public AbstractMathTaskGenerator(
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
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    protected final int minNumber;
    protected final int maxNumber;
    protected final EnumSet<Operation> availableOperations;
}
