package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;
import by.Lexus_FAMCS.quizer.exceptions.EmptyOperationsEnumSet;
import by.Lexus_FAMCS.quizer.exceptions.IncorrectTestCreated;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ExpressionTask implements Task {

    public static class Generator implements Task.Generator {
        private int minNumber;
        private int maxNumber;
        private List<Character> permittedSymbols = new ArrayList<>();
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<MathTask.Operation> operations
        ) {
            if (minNumber > maxNumber) throw new IllegalArgumentException("min is greater than max");
            if (operations.isEmpty()) throw new EmptyOperationsEnumSet();
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            if (operations.contains(MathTask.Operation.SUM)) permittedSymbols.add('+');
            if (operations.contains(MathTask.Operation.SUB)) permittedSymbols.add('-');
            if (operations.contains(MathTask.Operation.MULT)) permittedSymbols.add('*');
            if (operations.contains(MathTask.Operation.DIV)) permittedSymbols.add('/');
        }

        private int changeZero() {
            if (maxNumber == 0 && minNumber == 0) {
                    throw new IncorrectTestCreated("Incorrect test!!!");

            }
            return maxNumber > -minNumber ? (int) (Math.random() * (maxNumber) + 1) : (int) (Math.random() * (minNumber) - 1) ;
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
                case '/' -> {
                    if (num2 == 0) num2 = changeZero();
                    result = (double) num1 / num2;
                }
            }
            return new ExpressionTask("" + num1 + operator + num2 + "=?", result);
        }
    }
    private final double eps = 1e-6;
    private String text;
    private double result;
    public ExpressionTask(String text, double result) {
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
        return Math.abs((double) Math.round(result * 1000) / 1000 - ans) < eps ? Result.OK : Result.WRONG ;
    }
}
