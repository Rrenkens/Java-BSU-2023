package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.lang.reflect.Array;
import java.util.*;

class EquationTaskGenerator implements TaskGenerator {
    private int minNumber;
    private int maxNumber;
    private List<Character> permittedSymbols = new ArrayList<>();
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    EquationTaskGenerator(
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
    public EquationTask generate() {
        int num = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        int answer = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
        String result;
        boolean reverse = Math.random() > 0.5; // reverse is num<op>x=answer
        switch (operator) {
            case '+' -> result = Integer.toString(answer - num);
            case '-' -> result = reverse ? Integer.toString(num - answer) : Integer.toString(num + answer);
            case '*' -> result = answer + "/" + num;
            case '/' -> result = reverse ? num + "/" + answer : Integer.toString(num * answer);
            default -> result = "";
        }
        return new EquationTask("" + (reverse ? num : "x") + operator +
                (reverse ? "x" : num) + "=" + answer, result);
    }
}
