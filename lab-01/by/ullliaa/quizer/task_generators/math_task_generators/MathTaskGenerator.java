package by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber(); // получить минимальное число

    int getMaxNumber(); // получить максимальное число

    default int getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
