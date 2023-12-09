package by.BelArtem.quizer.task_generators.math_task_generators;


import by.BelArtem.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber();
    int getMaxNumber();

    default int getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
