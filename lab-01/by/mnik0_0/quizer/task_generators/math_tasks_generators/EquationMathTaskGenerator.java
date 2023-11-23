package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.TaskGenerator;
import by.mnik0_0.quizer.tasks.EquationTask;
import by.mnik0_0.quizer.tasks.ExpressionTask;
import by.mnik0_0.quizer.tasks.math_tasks.AbstractMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.EquationMathTask;

import java.util.ArrayList;
import java.util.Random;

class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    EquationMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
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

        int index = random.nextInt(operators.size());
        Character operator = operators.get(index);

        boolean firstNum = random.nextBoolean();

        if (firstNum) {
            equation.append(num).append(operator).append("x").append("=").append(res);
            switch (operator) {
                case '+':
                    answer = res - num;
                    break;
                case '-':
                    answer = num - res;
                    break;
                case '*':
                    if (num != 0) {
                        answer = (double) res / num;
                    } else {
                        return generate();
                    }
                    break;
                case '/':
                    if (res != 0) {
                        answer = (double) num / res;
                    } else {
                        return generate();
                    }
                    break;
            }
        } else {
            equation.append("x").append(operator).append(num).append("=").append(res);
            switch (operator) {
                case '+':
                    answer = res - num;
                    break;
                case '-':
                    answer = num + res;
                    break;
                case '*':
                    if (num != 0) {
                        answer = (double) res / num;
                    } else {
                        return generate();
                    }
                    break;
                case '/':
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

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

//    public static void main(String[] args) {
//        EquationMathTaskGenerator expressionTask = new EquationMathTaskGenerator(2, 10, true, true, true, true);
//        System.out.println(expressionTask.generate().getText());
//    }
}