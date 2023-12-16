package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

import by.KseniyaGnezdilova.quizer.tasks.Task;

public interface MathTask extends Task {

    interface Generator extends Task.Generator {

        double getMaxNumber();

        double getMinNumber();
        default double getDiffNumber(){
            return getMaxNumber() - getMinNumber();
        }

    }
}
