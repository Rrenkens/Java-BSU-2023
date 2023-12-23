package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.tasks.math_tasks.ExpressionTask;

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

    public ExpressionTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        StringBuilder builder = new StringBuilder();
        Random gener = new Random();
        int first = gener.nextInt(maxNumber_ - minNumber_ + 1) + minNumber_;
        builder.append(first);
        int second;
        int action;
        boolean flag = false;
        while (true) {
            second = gener.nextInt(maxNumber_ - minNumber_ + 1) + minNumber_;
            action = gener.nextInt(4);
            switch (action) {
                case 0:
                    if (generateSum_) {
                        builder.append('+');
                        flag = true;
                    }
                    break;
                case 1:
                    if (generateDifference_) {
                        builder.append('-');
                        flag = true;
                    }
                    break;
                case 2:
                    if (generateMultiplication_) {
                        builder.append('*');
                        flag = true;
                    }
                    break;
                case 3:
                    if (generateDivision_) {
                        builder.append('/');
                        flag = true;
                    }
                    break;
                default:
                    break;
            }
            if (flag) {
                break;
            }
        }
        if (action == 3 && second == 0) {
            while (second == 0) {
                second = gener.nextInt(maxNumber_ - minNumber_ + 1) + minNumber_;
            }
        }

        builder.append(second);
        builder.append("=?");

        double ans = 0;
        switch (action) {
            case 0:
                ans = first + second;
                break;
            case 1:
                ans = first - second;
                break;
            case 2:
                ans = first * second;
                break;
            case 3:
                ans = (double) first / second;
                break;
            default:
                break;
        }
        return new ExpressionTask(builder.toString(), ans);
    }
}