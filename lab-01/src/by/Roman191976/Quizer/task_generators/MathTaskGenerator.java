package by.Roman191976.Quizer.task_generators;

import by.Roman191976.Quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator{
    int getMinNumber(); // получить минимальное число
    int getMaxNumber(); // получить максимальное число

    /**
 * @return разница между максимальным и минимальным возможным числом
 */
default int getDiffNumber() {
    return getMaxNumber() - getMinNumber();
}
}
