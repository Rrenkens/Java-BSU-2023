package by.lamposhka.quizer.task_generators;

import by.lamposhka.quizer.tasks.EquationTask;

import java.util.ArrayList;
import java.util.Random;

public class EquationTaskGenerator implements TaskGenerator {
    private final ArrayList<EquationTaskGenerator.Operation> operators = new ArrayList<>(4);
    private final int minNumber;
    private final int maxNumber;
    private final Random random = new Random();

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
    public EquationTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (generateSum) {
            operators.add(EquationTaskGenerator.Operation.SUM);
        }
        if (generateDifference) {
            operators.add(EquationTaskGenerator.Operation.DIFFERENCE);
        }
        if (generateMultiplication) {
            operators.add(EquationTaskGenerator.Operation.MULTIPLICATION);
        }
        if (generateDivision) {
            operators.add(EquationTaskGenerator.Operation.DIVISION);
        }
    }

    /**
     * return задание типа {@link EquationTask}
     */
    public EquationTask generate() {
        String text;
        int answer;
        int number1 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int number2 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        int index = random.nextInt(operators.size());
        boolean xFirstPositionIndicator = random.nextBoolean();
        switch (operators.get(index)) {
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