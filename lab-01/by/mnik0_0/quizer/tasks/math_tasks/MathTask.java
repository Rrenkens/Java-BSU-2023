package by.mnik0_0.quizer.tasks.math_tasks;

import by.mnik0_0.quizer.Task;

public interface MathTask extends Task {
    public interface Generator extends Task.Generator {
        int getMinNumber(); // получить минимальное число
        int getMaxNumber(); // получить максимальное число

        default int getDiffNumber() {
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
