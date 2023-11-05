package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    private final ArrayList<AbstractMathTaskGenerator.Operation> operators = new ArrayList<>(4);
    private final int minNumber;
    private final int maxNumber;
    private final Random random = new Random();

    protected enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

    AbstractMathTaskGenerator( int minNumber,
                               int maxNumber,
                               boolean generateSum,
                               boolean generateDifference,
                               boolean generateMultiplication,
                               boolean generateDivision) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (generateSum) {
            operators.add(AbstractMathTaskGenerator.Operation.SUM);
        }
        if (generateDifference) {
            operators.add(AbstractMathTaskGenerator.Operation.DIFFERENCE);
        }
        if (generateMultiplication) {
            operators.add(AbstractMathTaskGenerator.Operation.MULTIPLICATION);
        }
        if (generateDivision) {
            operators.add(AbstractMathTaskGenerator.Operation.DIVISION);
        }
    }

    protected int generateNum() {
        return random.nextInt(maxNumber) + minNumber;
    }

    protected Operation generateOperator() {
        return operators.get(random.nextInt(operators.size()));
    }

    protected boolean generateVariablePositionIndicator() {
        return random.nextBoolean();
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }
    public abstract AbstractMathTask generate();

}
