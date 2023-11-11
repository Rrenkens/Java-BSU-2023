package by.DashaGnedko.quizer.tasks.math_tasks;

import by.DashaGnedko.quizer.tasks.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

}
