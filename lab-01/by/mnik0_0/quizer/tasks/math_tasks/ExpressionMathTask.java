package by.mnik0_0.quizer.tasks.math_tasks;


import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionMathTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations, int precision) {
            super(minNumber, maxNumber, operations, precision);
        }

        @Override
        public ExpressionMathTask generate() {
            StringBuilder expression = new StringBuilder();
            double answer = 0;

            Random random = new Random();

            double num1 = random.nextDouble(maxNumber - minNumber + 1) + minNumber;
            String formattedValue = decimalFormat.format(num1);
            num1 = Double.parseDouble(formattedValue);
            double num2 = random.nextDouble(maxNumber - minNumber + 1) + minNumber;
            formattedValue = decimalFormat.format(num2);
            num2 = Double.parseDouble(formattedValue);

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
                        answer = num1 / num2;
                        operationChar = '/';
                    } else {
                        return generate();
                    }
                    break;
            }

            expression.append(num1).append(operationChar).append(num2).append("=");
            formattedValue = decimalFormat.format(answer);
            answer = Double.parseDouble(formattedValue);
            return new ExpressionMathTask(expression.toString(), answer, precision);
        }

    }

//    public static void main(String[] args) {
//        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
//        ExpressionMathTask.Generator expressionTask = new ExpressionMathTask.Generator(2, 10, operations, 2);
//        System.out.println(expressionTask.generate().getText());
//    }

    public ExpressionMathTask(String text, double answer, int precision) {
        super(text, answer, precision);
    }
}
