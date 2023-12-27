package by.Roman191976.Quizer.task_generators.real_math_task_generators;

import by.Roman191976.Quizer.TaskGenerator;

public interface RealMathTaskGenerator extends TaskGenerator{
    double getMinNumber(); // получить минимальное число
    double getMaxNumber(); // получить максимальное число

    /**
 * @return разница между максимальным и минимальным возможным числом
 */
default double getDiffNumber() {
    return getMaxNumber() - getMinNumber();
}
}