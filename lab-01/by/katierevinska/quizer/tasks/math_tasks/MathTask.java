package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

public interface MathTask extends Task {
    @Override
    String getText();
    @Override
    Result validate(String answer);
}
