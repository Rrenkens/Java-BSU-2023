package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

public interface MathTask extends Task {

    public interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();
        default double getDiffNumber(){
            return getMaxNumber()-getMinNumber();
        }
        @Override
        Task generate() ;//TODO not throw
    }

    enum Operation{
        Sum,
        Difference,
        Multiplication,
        Division
    }
    @Override
    String getText();
    @Override
    Result validate(String answer);
}
