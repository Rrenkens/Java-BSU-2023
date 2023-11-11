package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.TaskGenerator;
import by.arteman17.quizer.tasks.math_tasks.EquationTask;

import java.util.Random;

public class EquationTaskGenerator extends AbstractMathTaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */

    public EquationTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        StringBuilder builder = new StringBuilder();
        Random gener = new Random();
        int pos = gener.nextInt(2);
        int first = gener.nextInt(maxNumber_ - minNumber_ + 1) + minNumber_;
        if (pos == 0) {
            builder.append('x');
        } else {
            builder.append(first);
        }
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

        if (pos == 0) {
            builder.append(first);
        } else {
            builder.append('x');
        }

        builder.append('=');
        builder.append(second);

        double ans = 0;
        if (pos == 0) {
            switch (action) {
                case 0:
                    ans = second - first;
                    break;
                case 1:
                    ans = first + second;
                    break;
                case 2:
                    ans = (double) second / first;
                    break;
                case 3:
                    ans = first * second;
                    break;
                default:
                    break;
            }
        } else {
            switch (action) {
                case 0:
                    ans = second - first;
                    break;
                case 1:
                    ans = first - second;
                    break;
                case 2:
                    ans = (double) second / first;
                    break;
                case 3:
                    ans = (double) first / second;
                    break;
                default:
                    break;
            }
        }
        return new EquationTask(builder.toString(), ans);
    }
}
