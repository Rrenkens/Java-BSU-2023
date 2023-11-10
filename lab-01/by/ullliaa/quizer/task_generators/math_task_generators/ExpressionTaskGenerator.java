package by.ullliaa.quizer.by.ullliaa.quizer.task_generators;

import by.ullliaa.quizer.by.ullliaa.quizer.TaskGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks.ExpressionMathTask;
import java.util.Random;

public class ExpressionTaskGenerator implements TaskGenerator {
    private final int minNumber_;
    private final int maxNumber_;
    private final boolean generateSum_;
    private final boolean generateDifference_;
    private final boolean generateMultiplication_;
    private final boolean generateDivision_;
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        minNumber_ = minNumber;
        maxNumber_ = maxNumber;
        generateSum_ = generateSum;
        generateDifference_ =  generateDifference;
        generateMultiplication_ = generateMultiplication;
        generateDivision_ = generateDivision;
    }

    /**
     * return задание типа {@link ExpressionMathTask}
     */
    public ExpressionMathTask generate() {
        int diff = maxNumber_ - minNumber_;
        Random random = new Random();
        int first = random.nextInt(diff + 1);
        first += minNumber_;
        int second = random.nextInt(diff + 1);
        second += minNumber_;

        int numbOfOperation = random.nextInt(4);

        StringBuilder operation = new StringBuilder();
        boolean tmp = true;
        while (tmp) {
            switch (numbOfOperation) {
                case 0:
                    if (!generateSum_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('+');
                    tmp = false;
                    break;
                case 1:
                    if (!generateDifference_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('-');
                    tmp = false;
                    break;
                case 2:
                    if (!generateMultiplication_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('*');
                    tmp = false;
                    break;
                case 3:
                    if (!generateDivision_) {
                        numbOfOperation = random.nextInt(4);
                        break;
                    }
                    operation.append('/');
                    tmp = false;
                    break;
                default:
                    numbOfOperation = random.nextInt(4);
                    break;
            }
        }

        return new ExpressionMathTask(first, second, operation.toString());
    }
}
