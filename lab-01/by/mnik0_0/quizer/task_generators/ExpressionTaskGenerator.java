package by.mnik0_0.quizer.task_generators;

import by.mnik0_0.quizer.TaskGenerator;
import by.mnik0_0.quizer.tasks.ExpressionTask;

import java.util.ArrayList;
import java.util.Random;

public class ExpressionTaskGenerator implements TaskGenerator {

    private int minNumber;
    private int maxNumber;
    private boolean generateSum;
    private boolean generateDifference;
    private boolean generateMultiplication;
    private boolean generateDivision;
    private ArrayList<Character> operators = new ArrayList<>();


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
            boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.generateSum = generateSum;
        this.generateDifference = generateDifference;
        this.generateMultiplication = generateMultiplication;
        this.generateDivision = generateDivision;

        if (generateSum) {
            operators.add('+');
        }
        if (generateDifference) {
            operators.add('-');
        }
        if (generateMultiplication) {
            operators.add('*');
        }
        if (generateSum) {
            operators.add('/');
        }
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    @Override
    public ExpressionTask generate() {
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

        return new ExpressionTask(expression.toString(), answer);
    }

//    public static void main(String[] args) {
//        ExpressionTaskGenerator expressionTask = new ExpressionTaskGenerator(2, 10, true, true, true, true);
//        System.out.println(expressionTask.generate().getText());
//    }
}