package by.lamposhka.quizer.tasks.math_tasks;

import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {

    public ExpressionTask(String text, double answer) {
        super(text, answer, 0);
    }

    public ExpressionTask(String text, double answer, int precision) {
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
            this(minNumber, maxNumber, 0, validOperations);
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
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() throws Exception {
            String text;
            double answer;
            double number1 = generateNum();
            double number2 = generateNum();

            switch (generateOperator()) {
                case SUM:
                    answer = number1 + number2;
                    text = number1 + "+" + number2;
                    break;
                case DIFFERENCE:
                    answer = number1 - number2;
                    text = number1 + "-" + number2;
                    break;
                case MULTIPLICATION:
                    answer = number1 * number2;
                    text = number1 + "*" + number2;
                    break;
                case DIVISION:
                    while (number2 == 0) {
                        number2 = generateNum();
                    }
                    answer = number1 / number2;
                    text = number1 + "/" + number2;
                    break;
                default:
                    throw new Exception("Unrecognized operator.");
            }
            return new ExpressionTask(text, castToPrecision(answer), precision);
        }
    }
}