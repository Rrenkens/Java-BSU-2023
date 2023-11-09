package by.Lexus_FAMCS.quizer.task_generators.math_task_generators;

import by.Lexus_FAMCS.quizer.tasks.Task;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    private int minNumber;
    private int maxNumber;
    protected List<Character> permittedSymbols = new ArrayList<>();
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    public AbstractMathTaskGenerator(
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

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    @Override
    public int getDiffNumber() {
        return maxNumber - minNumber;
    }

    protected double generateResultOfDivision(int a, int b) {
        if (b == 0) {
            if (permittedSymbols.size() == 1 && getMaxNumber() == 0 && getMinNumber() == 0) {
                throw new ArithmeticException("Incorrect test!!!");
            }
            b += getMaxNumber() >= 1 ? 1 : -1;
        }
        return (double) a / b;
    }

    public int generateInteger(int a, int b) {
        return (int) (Math.random() * (b - a + 1) + a);
    }
}
