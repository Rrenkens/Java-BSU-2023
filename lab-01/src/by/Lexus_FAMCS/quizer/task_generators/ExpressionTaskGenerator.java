package by.Lexus_FAMCS.quizer.task_generators;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.MathTask;

import java.lang.reflect.Array;
import java.util.*;

public class ExpressionTaskGenerator implements TaskGenerator {
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
    public ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operation> operations
    ) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        if (operations.contains(MathTask.Operation.SUM)) permittedSymbols.add('+');
        if (operations.contains(MathTask.Operation.SUB)) permittedSymbols.add('-');
        if (operations.contains(MathTask.Operation.MULT)) permittedSymbols.add('*');
        if (operations.contains(MathTask.Operation.DIV)) permittedSymbols.add('/');
    }

    private double generateResultOfDivision(int a, int b) {
        if (b == 0) {
            if (permittedSymbols.size() == 1 && maxNumber == 0 && minNumber == 0) {
                throw new ArithmeticException("Incorrect test!!!");
            }
            b += maxNumber >= 1 ? 1 : -1;
        }
        return (double) a / b;
    }


    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        int num1 = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        int num2 = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
        Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
        double result = Double.NaN;
        switch (operator) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = generateResultOfDivision(num1, num2);
        }
        return new ExpressionTask("" + num1 + operator + num2 + "=?", result);
    }
}
