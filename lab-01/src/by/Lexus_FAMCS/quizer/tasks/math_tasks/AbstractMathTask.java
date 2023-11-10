package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.Result;
import by.Lexus_FAMCS.quizer.exceptions.EmptyOperationsEnumSet;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractMathTask implements MathTask {
    protected static final double eps = 1e-6;
    protected static double div = 1;
    private String text;
    protected double result;
    static abstract class Generator implements MathTask.Generator {
        private double minNumber;
        private double maxNumber;
        private double precision;
        protected List<Character> permittedSymbols = new ArrayList<>();
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            if (minNumber - maxNumber > eps) throw new IllegalArgumentException("min is greater than max");
            if (operations.isEmpty()) throw new EmptyOperationsEnumSet();
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            if (operations.contains(MathTask.Operation.SUM)) permittedSymbols.add('+');
            if (operations.contains(MathTask.Operation.SUB)) permittedSymbols.add('-');
            if (operations.contains(MathTask.Operation.MULT)) permittedSymbols.add('*');
            if (operations.contains(MathTask.Operation.DIV)) permittedSymbols.add('/');
        }

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            if (minNumber - maxNumber > eps) throw new IllegalArgumentException("min is greater than max");
            if (precision < 0) throw new IllegalArgumentException("precision can't be negative");
            if (operations.isEmpty()) throw new EmptyOperationsEnumSet();
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            while (precision-- > 0) {
                div *= 10;
            }
            if (operations.contains(MathTask.Operation.SUM)) permittedSymbols.add('+');
            if (operations.contains(MathTask.Operation.SUB)) permittedSymbols.add('-');
            if (operations.contains(MathTask.Operation.MULT)) permittedSymbols.add('*');
            if (operations.contains(MathTask.Operation.DIV)) permittedSymbols.add('/');
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        @Override
        public double getDiffNumber() {
            return maxNumber - minNumber;
        }

        protected double generateResultOfDivision(double a, double b) {
            if (Math.abs(b) < eps) {
                if (permittedSymbols.size() == 1 &&
                        Math.abs(getMaxNumber()) < eps &&
                        Math.abs(getMaxNumber()) < eps) {
                    throw new ArithmeticException("Incorrect test!!!");
                }
                b = Math.abs(getMaxNumber()) < eps ? getMinNumber() : getMaxNumber();
            }
            return a / b;
        }

        public int generate(int a, int b) {
            return (int) (Math.random() * (b - a + 1) + a);
        }

        public double generate(double a, double b) {
            return (Math.random() * (b - a) + a);
        }
    }
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
