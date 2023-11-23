package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.TaskGenerator;
import by.mnik0_0.quizer.task_generators.math_tasks_generators.AbstractMathTaskGenerator;
import by.mnik0_0.quizer.tasks.ExpressionTask;
import by.mnik0_0.quizer.tasks.math_tasks.EquationMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.ArrayList;
import java.util.Random;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    ExpressionMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum, boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
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
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public ExpressionMathTask generate() {
        StringBuilder expression = new StringBuilder();
        double answer = 0;

        Random random = new Random();

        int num1 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int num2 = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        int index = random.nextInt(operators.size());
        Character operator = operators.get(index);

        switch (operator) {
            case '+':
                answer = num1 + num2;
                break;
            case '-':
                answer = num1 - num2;
                break;
            case '*':
                answer = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    answer = (double) num1 / num2;
                } else {
                    return generate();
                }
                break;
        }

        expression.append(num1).append(operator).append(num2).append("=");

        return new ExpressionMathTask(expression.toString(), answer);
    }

//    public static void main(String[] args) {
//        EquationMathTaskGenerator expressionTask = new EquationMathTaskGenerator(2, 10, true, true, true, true);
//        System.out.println(expressionTask.generate().getText());
//    }
}