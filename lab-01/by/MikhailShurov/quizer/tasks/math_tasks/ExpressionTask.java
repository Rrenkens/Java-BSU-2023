package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask.Operation;


import java.io.FilterOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class ExpressionTask extends AbstractMathTask {

    public ExpressionTask(
            String text,
            String answer
    ) {
        super(text, answer);
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }


    public static class Generator extends AbstractMathTask.Generator {
        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }


        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, precision,  operations);
            System.out.println(precision);
        }

        @Override
        public Task generate() {
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
            ExpressionTask task = null;

            if (selectedOperation == '/') {
                List<Integer> divisors = getDivisors(firstNumber);
                if (divisors != null) {
                    int randomIndex = random.nextInt(divisors.size());
                    secondNumber = divisors.get(randomIndex);
                    String taskStr = String.valueOf(firstNumber) + "/" + String.valueOf(secondNumber) + "=?";
                    task = new ExpressionTask(taskStr, String.valueOf(firstNumber / secondNumber));
                } else {
                    String taskStr = String.valueOf(firstNumber) + "/" + String.valueOf(secondNumber) + "=?";
                    double result = firstNumber / secondNumber;
                    result = Double.valueOf(decimalFormat.format(result));
                    task = new ExpressionTask(taskStr, String.valueOf(result));
                }
            } else {
                String taskStr = String.valueOf(firstNumber) + selectedOperation + String.valueOf(secondNumber) + "=?";
                task = new ExpressionTask(taskStr, String.valueOf(calculateSolution(firstNumber, secondNumber, selectedOperation)));
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
    }
}
