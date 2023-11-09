package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    static class Generator extends AbstractMathTask.Generator {
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public EquationTask generate() {
            int num = generateInteger(getMinNumber(), getMaxNumber());
            int answer = generateInteger(getMinNumber(), getMaxNumber());
            Character operator = permittedSymbols.get(generateInteger(0, permittedSymbols.size()));
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
