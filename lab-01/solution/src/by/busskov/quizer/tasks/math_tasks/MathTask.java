package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.Task;

public interface MathTask extends Task {
    public interface Generator extends Task.Generator {
        int getMinNumber();
        int getMaxNumber();
        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
