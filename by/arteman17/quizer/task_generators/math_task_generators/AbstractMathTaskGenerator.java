package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.Task;
import by.arteman17.quizer.tasks.math_tasks.AbstractMathTask;

public class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected final int minNumber_;
    protected final int maxNumber_;
    protected final boolean generateSum_;
    protected final boolean generateDifference_;
    protected final boolean generateMultiplication_;
    protected final boolean generateDivision_;
    public AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("Min is greater than max");
        }
        minNumber_ = minNumber;
        maxNumber_ = maxNumber;
        generateSum_ = generateSum;
        generateDifference_ = generateDifference;
        generateMultiplication_ = generateMultiplication;
        generateDivision_ = generateDivision;
    }

    @Override
    public AbstractMathTask generate() {
        return null;
    }

    @Override
    public int getMinNumber() {
        return minNumber_;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber_;
    }
}
