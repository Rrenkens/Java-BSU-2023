package by.lamposhka.quizer.tasks.math_tasks;

import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    public EquationTask(String text, double answer) {
        super(text, answer, 0);
    }

    public EquationTask(String text, double answer, int precision) {
        super(text, answer, precision);
    }

    public static class Generator extends AbstractMathTask.Generator {

        /**
         * @param minNumber       минимальное число
         * @param maxNumber       максимальное число
         * @param validOperations {@link EnumSet} с допустимыми операциями
         */
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<MathTask.Operation> validOperations
        ) {
            super(minNumber, maxNumber, 0, validOperations);
        }

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<MathTask.Operation> validOperations
        ) {
            super(minNumber, maxNumber, precision, validOperations);
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() throws Exception {
            String text;
            double answer;
            double number1 = generateNum();
            double number2 = generateNum();
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
                    throw new Exception("Unrecognized operator.");
            }
            return new EquationTask(text, castToPrecision(answer), precision);
        }
    }
}

