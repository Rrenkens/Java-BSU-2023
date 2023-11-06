package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;
import by.lamposhka.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    private final ArrayList<MathTask.Operation> operators = new ArrayList<>(4);
    private final int minNumber;
    private final int maxNumber;
    private final Random random = new Random();

    AbstractMathTaskGenerator(int minNumber,
                              int maxNumber,
                              EnumSet<MathTask.Operation> validOperations) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (validOperations.contains(MathTask.Operation.SUM)) {
            operators.add(MathTask.Operation.SUM);
        }
        if (validOperations.contains(MathTask.Operation.DIFFERENCE)) {
            operators.add(MathTask.Operation.DIFFERENCE);
        }
        if (validOperations.contains(MathTask.Operation.MULTIPLICATION)) {
            operators.add(MathTask.Operation.MULTIPLICATION);
        }
        if (validOperations.contains(MathTask.Operation.DIVISION)) {
            operators.add(MathTask.Operation.DIVISION);
        }
    }

    protected int generateNum() {
        return random.nextInt(maxNumber) + minNumber;
    }

    protected MathTask.Operation generateOperator() {
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
