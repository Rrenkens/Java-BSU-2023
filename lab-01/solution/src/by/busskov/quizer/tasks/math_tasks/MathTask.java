package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Task;

public interface MathTask extends Task {
    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
    public interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
