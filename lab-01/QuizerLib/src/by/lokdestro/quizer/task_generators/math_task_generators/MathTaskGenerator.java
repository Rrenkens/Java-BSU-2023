package by.lokdestro.quizer.task_generators.math_task_generators;

import by.lokdestro.quizer.task_generators.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber();
    int getMaxNumber();
    default int getDiffNumber() {
        return getMinNumber() - getMaxNumber();
    }
}