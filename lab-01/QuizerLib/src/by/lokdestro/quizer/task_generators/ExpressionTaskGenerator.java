package by.lokdestro.quizer.task_generators;

import by.lokdestro.quizer.tasks.ExpressionTask;

public class ExpressionTaskGenerator implements TaskGenerator {
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
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        operations = new boolean[4];
        this.operations[0] = generateSum;
        this.operations[1] = generateDifference;
        this.operations[2] = generateMultiplication;
        this.operations[3] = generateDivision;
    }

    /**
     * return задание типа {@link ExpressionTask}
     */

    public int GenerateNumber(int max, int min) {
        return (int)(Math.random()*(max-min+1)+min);

    }

    public ExpressionTask generate() {
        int num1 = GenerateNumber(maxNumber, minNumber);
        int num2 = GenerateNumber(maxNumber, minNumber);
        int operationNumber = GenerateNumber(3, 0);

        switch (operationNumber) {
            case 0:
                return new ExpressionTask(Integer.toString(num1 + num2),
                        Integer.toString(num1) + "+" + Integer.toString(num2) + "=?");
            case 1:
                return new ExpressionTask(Integer.toString(num1 - num2),
                        Integer.toString(num1) + "-" + Integer.toString(num2) + "=?");
            case 2:
                return new ExpressionTask(Integer.toString(num1 * num2),
                        Integer.toString(num1) + "*" + Integer.toString(num2) + "=?");
            default:
                while (num2 == 0) {
                    num2 = GenerateNumber(maxNumber, minNumber);
                }
                return new ExpressionTask(Integer.toString(num1 / num2),
                        Integer.toString(num1) + "/" + Integer.toString(num2) + "=?");
        }
    }

    int minNumber;
    int maxNumber;
    boolean[] operations;
}