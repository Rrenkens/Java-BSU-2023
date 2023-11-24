package by.Dzenia.quizer.task_generators.math_task_generators;

import by.Dzenia.quizer.Operation;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;
import by.Dzenia.quizer.tasks.math_tasks.EquationTask;
import by.Dzenia.quizer.tasks.math_tasks.ExpressionTask;

import java.util.EnumSet;

public class EquationTaskGenerator extends AbstractMathGenerator {
    public EquationTaskGenerator(double minNumber, double maxNumber, int precision, EnumSet<Operation> includedOperations) {
        super(minNumber, maxNumber, precision, includedOperations);
    }

    @Override
    public EquationTask generate() throws CannotGenerateTaskException {
        try {

            Operation operation = includedOperations.stream().toList().get(
                    generatePositiveInt() % includedOperations.size());
            EquationTask.TypeOfEquationTask type = EquationTask.TypeOfEquationTask.RIGHT_VARIABLE;
            if (generatePositiveInt() % 2 == 0) {
                type = EquationTask.TypeOfEquationTask.LEFT_VARIABLE;
            }
            double number = truncateDouble(generateDouble(), precision);
            double result = truncateDouble(generateDouble(), precision);
            return new EquationTask(number, result, operation, type, precision);
        } catch (Exception ignored) {
            throw new CannotGenerateTaskException("Failed attempt to generate a task");
        }
    }
}
