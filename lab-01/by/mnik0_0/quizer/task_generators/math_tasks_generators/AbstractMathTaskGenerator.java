package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.Task;
import by.mnik0_0.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {

    protected int minNumber;
    protected int maxNumber;
    protected boolean generateSum;
    protected boolean generateDifference;
    protected boolean generateMultiplication;
    protected boolean generateDivision;
    protected ArrayList<Character> operators = new ArrayList<>();

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
        this.generateSum = generateSum;
        this.generateDifference = generateDifference;
        this.generateMultiplication = generateMultiplication;
        this.generateDivision = generateDivision;

        if (generateSum) {
            operators.add('+');
        }
        if (generateDifference) {
            operators.add('-');
        }
        if (generateMultiplication) {
            operators.add('*');
        }
        if (generateSum) {
            operators.add('/');
        }
    }

    @Override
    public abstract MathTask generate();

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
}
