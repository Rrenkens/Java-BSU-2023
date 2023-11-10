package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.util.EnumSet;

public class ExpressionMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, operations);
        }

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, precision, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionMathTask generate() {
            double num1 = generate(getMinNumber(), getMaxNumber());
            double num2 = generate(getMinNumber(), getMaxNumber());
            Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
            double result = Double.NaN;
            switch (operator) {
                case '+' -> result = num1 + num2;
                case '-' -> result = num1 - num2;
                case '*' -> result = num1 * num2;
                case '/' -> {
                    if (Math.abs(num2) < eps) num2 = changeZero();
                    result = num1 / num2;
                }
            }
            return new ExpressionMathTask("" + num1 + operator + num2 + "=?", result, precision);
        }
    }
    ExpressionMathTask(String text, double result, int precision) { super(text, result, precision); }
}
