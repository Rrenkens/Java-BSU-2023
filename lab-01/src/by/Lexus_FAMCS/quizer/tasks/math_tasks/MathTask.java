package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.Task;

public interface MathTask extends Task {
    enum Operation {
        SUM,
        SUB,
        MULT,
        DIV
    }
}
