package by.fact0rial.quizer.tasks;

import by.fact0rial.quizer.Task;

import java.util.EnumSet;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
    }
    public enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
}
