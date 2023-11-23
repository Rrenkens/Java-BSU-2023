package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.TaskGenerator;
import by.mnik0_0.quizer.tasks.EquationTask;
import by.mnik0_0.quizer.tasks.ExpressionTask;
import by.mnik0_0.quizer.tasks.math_tasks.AbstractMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.EquationMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    EquationMathTaskGenerator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> operations) {
        super(minNumber, maxNumber, operations);
    }


    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */

    /**
     * return задание типа {@link EquationTask}
     */
    @Override
    public EquationMathTask generate() {
        StringBuilder equation = new StringBuilder();
        double answer = 0;

        Random random = new Random();

        int num = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int res = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        int index = random.nextInt(operations.size());
        MathTask.Operation operation = operations.stream().skip(index).findFirst().orElse(null);

        boolean firstNum = random.nextBoolean();

        char operationChar = ' ';

        switch (operation) {
            case Sum:
                operationChar = '+';
                break;
            case Difference:
                operationChar = '-';
                break;
            case Multiplication:
                operationChar = '*';
                break;
            case Division:
                operationChar = '/';
                break;

        }

        if (firstNum) {
            equation.append(num).append(operationChar).append("x").append("=").append(res);
            switch (operation) {
                case Sum:
                    answer = res - num;
                    break;
                case Difference:
                    answer = num - res;
                    break;
                case Multiplication:
                    if (num != 0) {
                        answer = (double) res / num;
                    } else {
                        return generate();
                    }
                    break;
                case Division:
                    if (res != 0) {
                        answer = (double) num / res;
                    } else {
                        return generate();
                    }
                    break;
            }
        } else {
            equation.append("x").append(operationChar).append(num).append("=").append(res);
            switch (operation) {
                case Sum:
                    answer = res - num;
                    break;
                case Difference:
                    answer = num + res;
                    break;
                case Multiplication:
                    if (num != 0) {
                        answer = (double) res / num;
                    } else {
                        return generate();
                    }
                    break;
                case Division:
                    if (num != 0) {
                        answer = (double) num * res;
                    } else {
                        return generate();
                    }
                    break;
            }
        }

        return new EquationMathTask(equation.toString(), answer);
    }

//    public static void main(String[] args) {
//        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
//        EquationMathTaskGenerator expressionTask = new EquationMathTaskGenerator(2, 10, operations);
//        System.out.println(expressionTask.generate().getText());
//    }
}