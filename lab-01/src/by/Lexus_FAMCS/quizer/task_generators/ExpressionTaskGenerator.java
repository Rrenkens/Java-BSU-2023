package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.lang.reflect.Array;
import java.util.*;

class ExpressionTaskGenerator implements TaskGenerator {
    int minNumber;
    int maxNumber;
    List<Character> permittedSymbols = new ArrayList<>();
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
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        if (generateSum) permittedSymbols.add('+');
        if (generateDifference) permittedSymbols.add('-');
        if (generateMultiplication) permittedSymbols.add('*');
        if (generateDivision) permittedSymbols.add('/');
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        int num1 = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        int num2 = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
        String result;
        switch (operator) {
            case '+' -> result = Integer.toString(num1 + num2);
            case '-' -> result = Integer.toString(num1 - num2);
            case '*' -> result = Integer.toString(num1 * num2);
            case '/' -> result = num1 + "/" + num2;
            default -> result = "";
        }
        return new ExpressionTask("" + num1 + operator + num2 + "=?", result);
    }
}
