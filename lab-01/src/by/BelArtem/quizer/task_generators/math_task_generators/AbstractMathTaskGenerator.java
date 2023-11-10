package by.BelArtem.quizer.task_generators.math_task_generators;

import by.BelArtem.quizer.tasks.ExpressionTask;
import by.BelArtem.quizer.tasks.math_tasks.MathTask;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected int minNumber;
    protected int maxNumber;
    protected boolean generateSum;
    protected boolean generateDifference;
    protected boolean generateMultiplication;
    protected boolean generateDivision;
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */

    protected AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException("minNumber is greater than maxNumber!");
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.generateSum = generateSum;
        this.generateDifference = generateDifference;
        this.generateMultiplication = generateMultiplication;
        this.generateDivision = generateDivision;
    }

    /**
     * return задание типа {@link ExpressionTask}
     */

    @Override
    public MathTask generate() throws Exception{
        return null;
    }

    @Override
    public  int getMinNumber() {
        return this.minNumber;
    }

    @Override
    public int getMaxNumber() {
        return this.maxNumber;
    }
}
