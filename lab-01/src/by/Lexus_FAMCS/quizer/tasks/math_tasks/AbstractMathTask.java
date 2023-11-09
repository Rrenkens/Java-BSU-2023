package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.Result;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator {
        private int minNumber;
        private int maxNumber;
        protected List<Character> permittedSymbols = new ArrayList<>();
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
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
    protected final double eps = 1e-6;
    protected double div = 1000;
    private String text;
    protected double result;
    AbstractMathTask(String text, double result) {
        this.text = text;
        this.result = result;
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        double ans;
        try {
            ans = Double.parseDouble(answer);
        } catch (NumberFormatException exc) {
            return Result.INCORRECT_INPUT;
        }
        return Math.abs(Math.round(result * div) / div - ans) < eps ? Result.OK : Result.WRONG ;
    }
}
