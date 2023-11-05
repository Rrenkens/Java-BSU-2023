package by.lamposhka.quizer.task_generators.math_task_generators;

import by.lamposhka.quizer.task_generators.TaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;
import by.lamposhka.quizer.tasks.math_tasks.EquationTask;

import java.util.ArrayList;
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
    public EquationTaskGenerator(
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
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        String text;
        int answer;
        int number1 = generateNum();
        int number2 = generateNum();
        boolean xFirstPositionIndicator = generateVariablePositionIndicator();
        switch (generateOperator()) {
            case SUM:
                text = (xFirstPositionIndicator) ? "x + " + number1 + " = " + number2 : number1 + " + x" + " = " + number2;
                answer = number2 - number1;
                break;
            case DIFFERENCE:
                text = (xFirstPositionIndicator) ? "x - " + number1 + " = " + number2 : number1 + " - x" + " = " + number2;
                answer = (xFirstPositionIndicator) ? number1 + number2 : number1 - number2;
                break;
            case MULTIPLICATION:
                text = (xFirstPositionIndicator) ? "x * " + number1 + " = " + number2 : number1 + " * x" + " = " + number2;
                answer = number2 / number1;
                break;
            case DIVISION:
                text = (xFirstPositionIndicator) ? "x / " + number1 + " = " + number2 : number1 + " / x" + " = " + number2;
                answer = (xFirstPositionIndicator) ? number1 * number2 : number1 / number2;
                break;
            default:
                text = "x = " + number1;
                answer = number1;
                break;
        }

        return new EquationTask(text, answer);
    }
}