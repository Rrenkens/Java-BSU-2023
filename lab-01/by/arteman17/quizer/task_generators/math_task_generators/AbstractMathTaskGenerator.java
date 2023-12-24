package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.exceptions.CantGenerateTask;
import by.arteman17.quizer.tasks.math_tasks.AbstractMathTask;
import by.arteman17.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected final int minNumber;
    protected final int maxNumber;
    protected List<MathTask.Operation> operations = new LinkedList<>();

    public AbstractMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> op
    ) {
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("Min is greater than max");
        }
        if (op == null) {
            throw new IllegalArgumentException("Operations set is null");
        }
        if (op.isEmpty()) {
            throw new CantGenerateTask("Operations set is empty");
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        operations.addAll(op);
    }

    @Override
    public abstract AbstractMathTask generate();

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
}
