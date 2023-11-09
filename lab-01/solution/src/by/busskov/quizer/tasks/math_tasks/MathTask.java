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
        int getMinNumber();
        int getMaxNumber();
        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
