package by.Lexus_FAMCS.quizer.task_generators.math_task_generators;

import by.Lexus_FAMCS.quizer.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
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
    AbstractMathTaskGenerator(
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

    public int generateInteger(int a, int b) {
        return (int) (Math.random() * (b - a + 1) + a);
    }
}