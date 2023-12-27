package by.Dzenia.quizer.task_generators.math_task_generators;
import by.Dzenia.quizer.tasks.math_tasks.ExpressionTask;
import by.Dzenia.quizer.Operation;
import java.util.EnumSet;

public class ExpressionTaskGenerator extends AbstractMathGenerator {
    public ExpressionTaskGenerator(double minNumber, double maxNumber, int precision, EnumSet<Operation> includedOperations) {
        super(minNumber, maxNumber, precision, includedOperations);
    }

    public ExpressionTask generate() {
        while (true) {
            try {
                int randomIndex = generatePositiveInt() % includedOperations.size();
                Operation operation = includedOperations.stream().toList().get(randomIndex);
                double firstValue = truncateDouble(generateDouble(), precision);
                double secondValue = truncateDouble(generateDouble(), precision);
                return new ExpressionTask(firstValue, secondValue, operation, precision);
            } catch (Exception ignored) {

            }
        }
    }
}