package by.katierevinska.quizer.task_generators.math_task_generators;

import by.katierevinska.quizer.Task;
import by.katierevinska.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    @Override
    Task generate() throws Exception;
}
