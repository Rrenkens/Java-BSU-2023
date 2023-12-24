package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.exceptions.CantGenerateTask;
import by.arteman17.quizer.tasks.math_tasks.EquationTask;
import by.arteman17.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.Random;

public class EquationTaskGenerator extends AbstractMathTaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     */

    public EquationTaskGenerator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> op) {
        super(minNumber, maxNumber, op);
    }

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        StringBuilder builder = new StringBuilder();
        Random gener = new Random();
        int pos = gener.nextInt(2);
        int first = gener.nextInt(getDiffNumber() + 1) + minNumber;
        if (pos == 0) {
            builder.append('x');
        } else {
            builder.append(first);
        }
        int second;
        MathTask.Operation action;
        boolean flag = false;
        do {
            second = gener.nextInt(getDiffNumber() + 1) + minNumber;
            action = operations.get(gener.nextInt(operations.size()));
            switch (action) {
                case SUM:
                    builder.append('+');
                    flag = true;
                    break;
                case DIFF:
                    builder.append('-');
                    flag = true;
                    break;
                case MUL:
                    builder.append('*');
                    flag = true;
                    break;
                case DIV:
                    builder.append('/');
                    flag = true;
                    break;
                default:
                    break;
            }
        } while (!flag);

        while ((action == MathTask.Operation.DIV && first == 0) || (action == MathTask.Operation.MUL && first == 0)) {
            first = gener.nextInt(getDiffNumber() + 1) + minNumber;
        }

        while (action == MathTask.Operation.DIV && pos == 1 && second == 0) {
            second = gener.nextInt(getDiffNumber() + 1) + minNumber;
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
                case SUM:
                    ans = second - first;
                    break;
                case DIFF:
                    ans = first + second;
                    break;
                case MUL:
                    ans = (double) second / first;
                    break;
                case DIV:
                    ans = first * second;
                    break;
                default:
                    break;
            }
        } else {
            switch (action) {
                case SUM:
                    ans = second - first;
                    break;
                case DIFF:
                    ans = first - second;
                    break;
                case MUL:
                    ans = (double) second / first;
                    break;
                case DIV:
                    ans = (double) first / second;
                    break;
                default:
                    break;
            }
        }
        return new EquationTask(builder.toString(), ans);
    }
}
