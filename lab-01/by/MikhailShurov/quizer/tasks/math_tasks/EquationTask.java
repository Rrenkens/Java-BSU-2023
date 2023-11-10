package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;

import java.text.DecimalFormat;
import java.util.*;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask.Operation;

public class EquationTask extends AbstractMathTask {

    public EquationTask(
            String text,
            String answer
    ) {
        super(text, answer);
    }

    /**
     * @return 
     */
    @Override
    public String getAnswer() {
        return answer;
    }

    public static class Generator extends AbstractMathTask.Generator {

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, precision, operations);
        }

        @Override
        public EquationTask generate() {
            Random random = new Random();
            DecimalFormat decimalFormat;

            if (precision > 0) {
                decimalFormat = new DecimalFormat("#." + "#".repeat(precision));
            } else {
                decimalFormat = new DecimalFormat("#");
            }

            double firstNumber = random.nextDouble(this.maxNumber - this.minNumber + 1) + this.minNumber;
            firstNumber = Double.valueOf(decimalFormat.format(firstNumber));

            double secondNumber = random.nextDouble(this.maxNumber - this.minNumber + 1) + this.minNumber;
            secondNumber = Double.valueOf(decimalFormat.format(secondNumber));


            if (secondNumber > firstNumber) {
                double temp = firstNumber;
                firstNumber = secondNumber;
                secondNumber = temp;
            }

            int randomNumber = random.nextInt(2);
            boolean xInFirstPosition = (randomNumber != 0);
            char selectedOperation = operationsList.get(random.nextInt(operationsList.size()));
            EquationTask task = null;

            if (selectedOperation == '/' && secondNumber == 0) {
                return generate();
            }
            if (xInFirstPosition) {
                if (selectedOperation == '/') {
                    if (firstNumber == 0) {
                        return generate();
                    }
                    String taskStr = "x/" + String.valueOf(firstNumber) + "=" + String.valueOf(secondNumber);
                    double result = firstNumber * secondNumber;
                    result = Double.valueOf(decimalFormat.format(result));
                    task = new EquationTask(taskStr, String.valueOf(result));
                } else {
                    String taskStr = "x" + selectedOperation + secondNumber + "=" + calculateSolution(firstNumber, secondNumber, selectedOperation);
                    task = new EquationTask(taskStr, String.valueOf(firstNumber));
                }
            } else {
                if (selectedOperation == '/') {
                    List<Integer> divisors = getDivisors(firstNumber);
                    if (divisors != null) {
                        int randomIndex = random.nextInt(divisors.size());
                        int answer = divisors.get(randomIndex);
                        String taskStr = String.valueOf(firstNumber) + "/x=" + String.valueOf(answer);
                        task = new EquationTask(taskStr, String.valueOf(firstNumber / answer));
                    } else {
                        double answer = random.nextDouble(this.maxNumber - this.minNumber + 1) + this.minNumber;
                        answer = Double.valueOf(decimalFormat.format(answer));
                        System.out.println(answer);
                        String taskStr = String.valueOf(firstNumber) + "/x=" + String.valueOf(answer);
                        double result = firstNumber / answer;
                        result = Double.valueOf(decimalFormat.format(result));
                        task = new EquationTask(taskStr, String.valueOf(result));
                    }

                } else {
                    String taskStr = String.valueOf(firstNumber) + selectedOperation + "x=" + calculateSolution(firstNumber, secondNumber, selectedOperation);
                    task = new EquationTask(taskStr, String.valueOf(secondNumber));
                }
            }
            return task;
        }

        private static List<Integer> getDivisors(double number) {
            if (number == (int)number) {
                List<Integer> divisors = new ArrayList<>();
                int absNumber = Math.abs((int)number); // Получаем модуль числа
                for (int i = 1; i <= absNumber; i++) {
                    if (absNumber % i == 0) {
                        divisors.add(i);
                        if (i != absNumber / i) {
                            divisors.add(-i);
                        }
                    }
                }
                return divisors;
            }
            return null;
        }

        private double calculateSolution(double firstNumber, double secondNumber, char operation) {
            DecimalFormat decimalFormat;
            if (precision > 0) {
                decimalFormat = new DecimalFormat("#." + "#".repeat(precision));
            } else {
                decimalFormat = new DecimalFormat("#");
            }
            switch (operation) {
                case '+':
                    return Double.valueOf(decimalFormat.format(firstNumber + secondNumber));
                case '-':
                    return Double.valueOf(decimalFormat.format(firstNumber - secondNumber));
                case '*':
                    return Double.valueOf(decimalFormat.format(firstNumber * secondNumber));
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operation);
            }
        }

        /**
         * @return
         */
        @Override
        public double getMinNumber() {
            return minNumber;
        }

        /**
         * @return
         */
        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
    }
}
