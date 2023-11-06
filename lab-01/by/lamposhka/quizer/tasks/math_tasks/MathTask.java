package by.lamposhka.quizer.tasks.math_tasks;

import by.lamposhka.quizer.tasks.Task;

public interface MathTask extends Task {
    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

    interface Generator extends Task.Generator {

        int getMinNumber(); // получить минимальное число

        int getMaxNumber(); // получить максимальное число

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
