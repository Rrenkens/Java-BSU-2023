package by.mnik0_0.quizer.tasks.math_tasks;


import java.util.EnumSet;
import java.util.Random;

public class ExpressionMathTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
        Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public ExpressionMathTask generate() {
            StringBuilder expression = new StringBuilder();
            double answer = 0;

            Random random = new Random();

            int num1 = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int num2 = random.nextInt(maxNumber - minNumber + 1) + minNumber;

            int index = random.nextInt(operations.size());
            MathTask.Operation operation = operations.stream().skip(index).findFirst().orElse(null);

            char operationChar = ' ';
            switch (operation) {
                case Sum:
                    answer = num1 + num2;
                    operationChar = '+';
                    break;
                case Difference:
                    answer = num1 - num2;
                    operationChar = '-';
                    break;
                case Multiplication:
                    answer = num1 * num2;
                    operationChar = '*';
                    break;
                case Division:
                    if (num2 != 0) {
                        answer = (double) num1 / num2;
                        operationChar = '/';
                    } else {
                        return generate();
                    }
                    break;
            }

            expression.append(num1).append(operationChar).append(num2).append("=");

            return new ExpressionMathTask(expression.toString(), answer);
        }

    }

//    public static void main(String[] args) {
//        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
//        ExpressionMathTask.Generator expressionTask = new ExpressionMathTask.Generator(2, 10, operations);
//        System.out.println(expressionTask.generate().getText());
//    }

    public ExpressionMathTask(String text, double answer) {
        super(text, answer);
    }
}
