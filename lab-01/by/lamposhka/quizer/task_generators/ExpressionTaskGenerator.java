package by.lamposhka.quizer.task_generators;

import by.lamposhka.quizer.TaskGenerator;
import by.lamposhka.quizer.tasks.ExpressionTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

class ExpressionTaskGenerator implements TaskGenerator {
    private ArrayList<Operation> operators = new ArrayList<Operation>(4);
    private final int minNumber;
    private final int maxNumber;
    private Random random = new Random();

    private enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

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
        if (generateSum) {
            operators.add(Operation.SUM);
        }
        if (generateDifference) {
            operators.add(Operation.DIFFERENCE);
        }
        if (generateMultiplication) {
            operators.add(Operation.MULTIPLICATION);
        }
        if (generateDivision) {
            operators.add(Operation.DIVISION);
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        String text;
        int answer;
        int number1 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int number2 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int index = random.nextInt(operators.size());

        switch (operators.get(index)) {
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