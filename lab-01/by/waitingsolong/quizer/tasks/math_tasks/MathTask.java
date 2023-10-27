package by.waitingsolong.quizer.tasks.math_tasks;

import by.waitingsolong.quizer.Result;
import by.waitingsolong.quizer.Task;

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

        /**
         * return задание типа {@link EquationTask}
         */
        @Override
        abstract public AbstractMathTask generate();
    }
    @Override
    abstract public String getText();

    @Override
    abstract public Result validate(String answer);
}