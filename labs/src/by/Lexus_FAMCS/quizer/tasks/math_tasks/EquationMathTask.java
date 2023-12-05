package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.exceptions.IncorrectTestCreated;
import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
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

        public EquationMathTask generate() {
            double num = generate(getMinNumber(), getMaxNumber());
            double answer = generate(getMinNumber(), getMaxNumber());
            Character operator = permittedSymbols.get((int) (Math.random() * permittedSymbols.size()));
            double result = Double.NaN;
            boolean reverse = Math.random() > 0.5; // reverse is num<op>x=answer
            switch (operator) {
                case '+' -> result = answer - num;
                case '-' -> result = reverse ? num - answer : num + answer;
                case '*' -> {
                    if (Math.abs(num) < eps) num = changeZero();
                    result = answer / num;
                }
                case '/' -> {
                    // case 1: 0/x=answer
                    // case 2: x/0=answer
                    if (Math.abs(num) < eps) num = changeZero();
                    if (!reverse) {
                        result = num * answer;
                    }
                    else {
                        if (Math.abs(answer) < eps) answer = changeZero();
                        result = num / answer;
                    }
                }

            }
            return new EquationMathTask("" + (reverse ? num : "x") + operator +
                    (reverse ? "x" : num) + "=" + answer, result, precision);
        }
    }
    EquationMathTask(String text, double result, int precision) { super(text, result, precision); }
}
