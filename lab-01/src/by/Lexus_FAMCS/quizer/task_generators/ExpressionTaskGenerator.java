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
        String num1 = Integer.toString((int) (Math.random() * (maxNumber - minNumber + 1) + minNumber));
        String num2 = Integer.toString((int) (Math.random() * (maxNumber - minNumber + 1) + minNumber));
        return new ExpressionTask(num1 +
                permittedSymbols.get((int) (Math.random() * (permittedSymbols.size() + 1))) +
                num2);
    }
}
