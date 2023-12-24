package by.arteman17.quizer.task_generators.math_task_generators;

import by.arteman17.quizer.tasks.math_tasks.ExpressionTask;
import by.arteman17.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.Random;


public class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
    /**
     * @param minNumber минимальное число
     * @param maxNumber максимальное число
     */

    public ExpressionTaskGenerator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> op) {
        super(minNumber, maxNumber, op);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        StringBuilder builder = new StringBuilder();
        Random gener = new Random();
        int first = gener.nextInt(getDiffNumber() + 1) + minNumber;
        builder.append(first);
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
        while (action == MathTask.Operation.DIV && second == 0) {
            second = gener.nextInt(getDiffNumber() + 1) + minNumber;
        }

        builder.append(second);
        builder.append("=?");

        double ans = 0;
        switch (action) {
            case SUM:
                ans = first + second;
                break;
            case DIFF:
                ans = first - second;
                break;
            case MUL:
                ans = first * second;
                break;
            case DIV:
                ans = (double) first / second;
                break;
            default:
                break;
        }
        return new ExpressionTask(builder.toString(), ans);
    }
}