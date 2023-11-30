package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Task;
import by.ullliaa.quizer.by.ullliaa.quizer.TaskGenerator;

enum Operation{
    SUM,
    DIFF,
    DIVISION,
    MULTI
}

public interface MathTask extends Task {
    Operation operation = null;

    public default Operation getOperation() {
        return operation;
    }
}
