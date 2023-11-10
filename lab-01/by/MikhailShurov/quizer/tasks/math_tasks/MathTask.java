package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;

public interface MathTask extends Task {
    public enum Operation {
        Sum, Difference, Multiplication, Division
    }
    interface Generator extends Task.Generator {
        double getMinNumber(); // получить минимальное число
        double getMaxNumber(); // получить максимальное число

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

    }

}
