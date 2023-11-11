package by.nrydo.quizer.task_generators.task_genertors;

import by.nrydo.quizer.TaskGenerator;
import by.nrydo.quizer.tasks.math_tasks.AbstractMathTask;
import by.nrydo.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.ArrayList;
import java.util.Random;

public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    /**
     * return задание типа {@link ExpressionMathTask}
     */
    @Override
    public ExpressionMathTask generate() {
        var random = new Random();
        while (true) {
            try {
                var x = random.nextInt(maxNumber - minNumber + 1) + minNumber;
                var y = random.nextInt(maxNumber - minNumber + 1) + minNumber;
                var operation = operations.get(random.nextInt(operations.size()));
                return new ExpressionMathTask(x, y, operation);
            } catch (Exception ignored) {
            }
        }
    }
}