package by.fact0rial.quizer.tasks.math_tasks;

import by.fact0rial.quizer.Task;

import java.util.EnumSet;

public interface MathTask extends Task {

    interface Generator extends Task.Generator {
        public double getMinNumber();
        public double getMaxNumber();
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
    public enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
}
