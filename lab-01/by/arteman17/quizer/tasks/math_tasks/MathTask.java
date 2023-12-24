package by.arteman17.quizer.tasks.math_tasks;

import by.arteman17.quizer.Task;
public interface MathTask extends Task {
    public enum Operation {
        SUM,
        DIFF,
        MUL,
        DIV
    }
}
