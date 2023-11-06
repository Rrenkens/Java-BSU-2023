package by.lamposhka.quizer.tasks.math_tasks;

import by.lamposhka.quizer.tasks.math_tasks.AbstractMathTask;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {

    public ExpressionTask(String text, int answer) {
        super(text, answer);
    }

    public static class Generator extends AbstractMathTask.Generator {

        /**
         * @param minNumber       минимальное число
         * @param maxNumber       максимальное число
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
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            String text;
            int answer;
            int number1 = generateNum();
            int number2 = generateNum();

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
                    answer = number1;
                    text = number1 + "";
            }
            return new ExpressionTask(text, answer);
        }
    }
}
