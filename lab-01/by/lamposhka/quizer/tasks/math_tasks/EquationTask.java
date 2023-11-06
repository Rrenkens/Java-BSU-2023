package by.lamposhka.quizer.tasks.math_tasks;
import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    public EquationTask(String text, int answer) {
        super(text, answer);
    }
    public static class Generator extends AbstractMathTask.Generator {

        /**
         * @param minNumber  минимальное число
         * @param maxNumber  максимальное число
         * @param validOperations {@link EnumSet} с допустимыми операциями
         */
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<MathTask.Operation> validOperations
        ) {
            super(minNumber, maxNumber, validOperations);
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            String text;
            int answer;
            int number1 = generateNum();
            int number2 = generateNum();
            boolean xFirstPositionIndicator = generateVariablePositionIndicator();
            switch (generateOperator()) {
                case SUM:
                    text = (xFirstPositionIndicator) ? "x + " + number1 + " = " + number2 : number1 + " + x" + " = " + number2;
                    answer = number2 - number1;
                    break;
                case DIFFERENCE:
                    text = (xFirstPositionIndicator) ? "x - " + number1 + " = " + number2 : number1 + " - x" + " = " + number2;
                    answer = (xFirstPositionIndicator) ? number1 + number2 : number1 - number2;
                    break;
                case MULTIPLICATION:
                    text = (xFirstPositionIndicator) ? "x * " + number1 + " = " + number2 : number1 + " * x" + " = " + number2;
                    answer = number2 / number1;
                    break;
                case DIVISION:
                    text = (xFirstPositionIndicator) ? "x / " + number1 + " = " + number2 : number1 + " / x" + " = " + number2;
                    answer = (xFirstPositionIndicator) ? number1 * number2 : number1 / number2;
                    break;
                default:
                    text = "x = " + number1;
                    answer = number1;
                    break;
            }

            return new EquationTask(text, answer);
        }
    }
}

