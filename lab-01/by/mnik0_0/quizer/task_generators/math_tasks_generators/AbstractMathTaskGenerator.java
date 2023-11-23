package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.Task;
import by.mnik0_0.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {

    protected int minNumber;
    protected int maxNumber;
    protected EnumSet<MathTask.Operation> operations;

    AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> operations
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.operations = operations;

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
