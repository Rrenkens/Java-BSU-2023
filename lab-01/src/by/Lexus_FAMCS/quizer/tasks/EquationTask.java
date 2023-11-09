package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EquationTask implements Task {

    public static class Generator implements Task.Generator {
        private int minNumber;
        private int maxNumber;
        private List<Character> permittedSymbols = new ArrayList<>();
        public Generator(
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

        public EquationTask generate() {
            int num = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
            int answer = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);
            Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
            double result = Double.NaN;
            boolean reverse = Math.random() > 0.5; // reverse is num<op>x=answer
            switch (operator) {
                case '+' -> result = answer - num;
                case '-' -> result = reverse ? num - answer : num + answer;
                case '*' -> result = generateResultOfDivision(answer, num);
                case '/' -> result = reverse ? generateResultOfDivision(num, answer) : num * answer;

            }
            return new EquationTask("" + (reverse ? num : "x") + operator +
                    (reverse ? "x" : num) + "=" + answer, result);
        }
    }
    private final double eps = 1e-6;
    private String text;
    private double result;
    public EquationTask(String text, double result) {
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