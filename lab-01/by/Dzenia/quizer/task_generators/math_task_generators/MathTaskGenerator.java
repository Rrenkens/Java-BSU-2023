package by.Dzenia.quizer.task_generators.math_task_generators;

import by.Dzenia.quizer.task_generators.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    double getMinNumber(); // получить минимальное число
    double getMaxNumber(); // получить максимальное число
    default double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
