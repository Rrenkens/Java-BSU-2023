package by.mnik0_0.quizer.tasks.math_tasks;


import java.util.EnumSet;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations, int precision) {
            super(minNumber, maxNumber, operations, precision);
        }

        @Override
        public EquationMathTask generate() {
            StringBuilder equation = new StringBuilder();
            double answer = 0;

            Random random = new Random();

            double num = random.nextDouble(maxNumber - minNumber + 1) + minNumber;
            String formattedValue = decimalFormat.format(num);
            num = Double.parseDouble(formattedValue);
            double res = random.nextDouble(maxNumber - minNumber + 1) + minNumber;
            formattedValue = decimalFormat.format(res);
            res = Double.parseDouble(formattedValue);

            int index = random.nextInt(operations.size());
            MathTask.Operation operation = operations.stream().skip(index).findFirst().orElse(null);

            boolean firstNum = random.nextBoolean();

            char operationChar = ' ';

            switch (operation) {
                case Sum:
                    operationChar = '+';
                    break;
                case Difference:
                    operationChar = '-';
                    break;
                case Multiplication:
                    operationChar = '*';
                    break;
                case Division:
                    operationChar = '/';
                    break;

            }

            if (firstNum) {
                equation.append(num).append(operationChar).append("x").append("=").append(res);
                switch (operation) {
                    case Sum:
                        answer = res - num;
                        break;
                    case Difference:
                        answer = num - res;
                        break;
                    case Multiplication:
                        if (num != 0) {
                            answer = (double) res / num;
                        } else {
                            return generate();
                        }
                        break;
                    case Division:
                        if (res != 0) {
                            answer = (double) num / res;
                        } else {
                            return generate();
                        }
                        break;
                }
            } else {
                equation.append("x").append(operationChar).append(num).append("=").append(res);
                switch (operation) {
                    case Sum:
                        answer = res - num;
                        break;
                    case Difference:
                        answer = num + res;
                        break;
                    case Multiplication:
                        if (num != 0) {
                            answer = res / num;
                        } else {
                            return generate();
                        }
                        break;
                    case Division:
                        if (num != 0) {
                            answer = num * res;
                        } else {
                            return generate();
                        }
                        break;
                }
            }
            formattedValue = decimalFormat.format(answer);
            answer = Double.parseDouble(formattedValue);
            return new EquationMathTask(equation.toString(), answer, precision);
        }
    }

    public EquationMathTask(String text, double answer, int precision) {
        super(text, answer, precision);
    }
}
