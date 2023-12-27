package by.Dzenia.quizer.tasks.math_tasks;
import by.Dzenia.quizer.tasks.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
