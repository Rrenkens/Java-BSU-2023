package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.util.EnumSet;

public class ExpressionMathTask extends AbstractMathTask {
    static class Generator extends AbstractMathTask.Generator {
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
        public ExpressionTask generate() {
            double num1 = generate(getMinNumber(), getMaxNumber());
            double num2 = generate(getMinNumber(), getMaxNumber());
            Character operator = permittedSymbols.get(generate(0, permittedSymbols.size() - 1));
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
    ExpressionMathTask(String text, double result) { super(text, result); }
}
