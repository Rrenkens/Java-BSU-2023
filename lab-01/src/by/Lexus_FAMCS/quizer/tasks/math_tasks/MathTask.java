package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        int getMinNumber();
        int getMaxNumber();
        int getDiffNumber();
    }
    enum Operation {
        SUM,
        SUB,
        MULT,
        DIV
    }
}
