package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
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
        public EquationTask generate() {
            double num = generate(getMinNumber(), getMaxNumber());
            double answer = generate(getMinNumber(), getMaxNumber());
            Character operator = permittedSymbols.get(generate(0, permittedSymbols.size() - 1));
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
    EquationMathTask(String text, double result) { super(text, result); }
}
