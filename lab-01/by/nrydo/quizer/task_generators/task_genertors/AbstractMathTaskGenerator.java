package by.nrydo.quizer.task_generators.task_genertors;

import by.nrydo.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.ArrayList;

abstract public class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected final int minNumber;
    protected final int maxNumber;
    protected ArrayList<AbstractMathTask.Operation> operations;

    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        operations = new ArrayList<>();
        if (generateSum) {
            operations.add(AbstractMathTask.Operation.SUM);
        }
        if (generateDifference) {
            operations.add(AbstractMathTask.Operation.Difference);
        }
        if (generateMultiplication) {
            operations.add(AbstractMathTask.Operation.Multiplication);
        }
        if (generateDivision) {
            operations.add(AbstractMathTask.Operation.Division);
        }
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
