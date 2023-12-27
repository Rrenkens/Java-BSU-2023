package by.Dzenia.quizer.task_generators.math_task_generators;
import by.Dzenia.quizer.Operation;
import by.Dzenia.quizer.tasks.math_tasks.EquationTask;
import java.util.EnumSet;

public class EquationTaskGenerator extends AbstractMathGenerator {
    public EquationTaskGenerator(double minNumber, double maxNumber, int precision, EnumSet<Operation> includedOperations) {
        super(minNumber, maxNumber, precision, includedOperations);
    }

    @Override
    public EquationTask generate() {
        while (true) {
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

            }
        }
    }
}
