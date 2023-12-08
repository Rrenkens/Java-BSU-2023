package by.mnik0_0.quizer.tasks.math_tasks;

import by.mnik0_0.quizer.Task;

public interface MathTask extends Task {
    public interface Generator extends Task.Generator {
        double getMinNumber(); // получить минимальное число
        double getMaxNumber(); // получить максимальное число

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
    public enum Operation{
        Sum,
        Difference,
        Multiplication,
        Division
    }
}
