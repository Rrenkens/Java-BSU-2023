package by.AlexHanimar.quizer.tasks.math_tasks;

import by.AlexHanimar.quizer.Task;

public interface MathTask extends Task {
    public interface Generator extends Task.Generator {
        public double getMinNumber();
        public double getMaxNumber();

        public default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    public enum Operation {
        SUM,
        DIFF,
        MUL,
        DIV
    };
}
