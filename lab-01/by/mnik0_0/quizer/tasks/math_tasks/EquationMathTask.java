package by.mnik0_0.quizer.tasks.math_tasks;


import java.util.EnumSet;
import java.util.Random;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public EquationMathTask generate() {
            StringBuilder equation = new StringBuilder();
            double answer = 0;

            Random random = new Random();

            int num = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int res = random.nextInt(maxNumber - minNumber + 1) + minNumber;

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
                            answer = (double) res / num;
                        } else {
                            return generate();
                        }
                        break;
                    case Division:
                        if (num != 0) {
                            answer = (double) num * res;
                        } else {
                            return generate();
                        }
                        break;
                }
            }

            return new EquationMathTask(equation.toString(), answer);
        }
    }

//    public static void main(String[] args) {
//        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
//        EquationMathTask.Generator expressionTask = new EquationMathTask.Generator(2, 10, operations);
//        System.out.println(expressionTask.generate().getText());
//    }

    public EquationMathTask(String text, double answer) {
        super(text, answer);
    }
}
