package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.ExpressionTask;

import java.util.ArrayList;
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

    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        String text;
        int answer;
        int number1 = generateNum();
        int number2 = generateNum();

        switch (generateOperator()) {
            case SUM :
                answer = number1 + number2;
                text = number1 + "+" + number2;
                break;
            case DIFFERENCE:
                answer = number1 - number2;
                text = number1 + "-" + number2;
                break;
            case MULTIPLICATION:
                answer = number1 * number2;
                text = number1 + "*" + number2;
                break;
            case DIVISION:
                while (number2 == 0) {
                    number2 = generateNum();
                }
                answer = number1 / number2;
                text = number1 + "/" + number2;
                break;
            default:
                answer = number1;
                text = number1 + "";
        }
        return new ExpressionTask(text, answer);
    }
}