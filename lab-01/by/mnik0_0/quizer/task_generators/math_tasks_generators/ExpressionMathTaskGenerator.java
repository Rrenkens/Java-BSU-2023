package by.mnik0_0.quizer.task_generators.math_tasks_generators;

import by.mnik0_0.quizer.TaskGenerator;
import by.mnik0_0.quizer.task_generators.ExpressionTaskGenerator;
import by.mnik0_0.quizer.task_generators.math_tasks_generators.AbstractMathTaskGenerator;
import by.mnik0_0.quizer.tasks.ExpressionTask;
import by.mnik0_0.quizer.tasks.math_tasks.EquationMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.ExpressionMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    ExpressionMathTaskGenerator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> operations) {
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
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public ExpressionMathTask generate() {
        StringBuilder expression = new StringBuilder();
        double answer = 0;

        Random random = new Random();

        int num1 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int num2 = random.nextInt(maxNumber - minNumber + 1) + minNumber;

        int index = random.nextInt(operations.size());
        MathTask.Operation operation = operations.stream().skip(index).findFirst().orElse(null);

        char operationChar = ' ';
        switch (operation) {
            case Sum:
                answer = num1 + num2;
                operationChar = '+';
                break;
            case Difference:
                answer = num1 - num2;
                operationChar = '-';
                break;
            case Multiplication:
                answer = num1 * num2;
                operationChar = '*';
                break;
            case Division:
                if (num2 != 0) {
                    answer = (double) num1 / num2;
                    operationChar = '/';
                } else {
                    return generate();
                }
                break;
        }

        expression.append(num1).append(operationChar).append(num2).append("=");

        return new ExpressionMathTask(expression.toString(), answer);
    }

//    public static void main(String[] args) {
//        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
//        ExpressionMathTaskGenerator expressionTask = new ExpressionMathTaskGenerator(2, 10, operations);
//        System.out.println(expressionTask.generate().getText());
//    }
}