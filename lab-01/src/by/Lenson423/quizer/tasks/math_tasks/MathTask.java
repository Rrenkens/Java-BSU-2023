package by.Lenson423.quizer.tasks.math_tasks;

import by.Lenson423.quizer.Task;

public interface MathTask extends Task {

    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

    interface Generator extends Task.Generator {
        double getMinNumber(); // получить минимальное число

        double getMaxNumber(); // получить максимальное число

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
