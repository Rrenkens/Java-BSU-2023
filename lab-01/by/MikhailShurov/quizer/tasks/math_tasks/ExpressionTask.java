package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask.Operation;


import java.util.*;

public class ExpressionTask extends AbstractMathTask {
    String text;
    String answer;

    public ExpressionTask(
            String text,
            String answer
    ) {
        super(text, answer);
        this.text = text;
        this.answer = answer;
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

        int minNumber;
        int maxNumber;

        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public Task generate() {
            Random random = new Random();

            int firstNumber = random.nextInt(minNumber, maxNumber + 1);
            int secondNumber = random.nextInt(minNumber, maxNumber + 1);
            // swap if secondNumber > firstNumber
            if (secondNumber > firstNumber) {
                firstNumber = firstNumber ^ secondNumber;
                secondNumber = firstNumber ^ secondNumber;
                firstNumber = firstNumber ^ secondNumber;
            }

            int randomNumber = random.nextInt(2);
            boolean xInFirstPosition = (randomNumber != 0);
            char selectedOperation = operationsList.get(random.nextInt(operationsList.size()));
            ExpressionTask task = null;

            if (selectedOperation == '/') {
                List<Integer> divisors = getDivisors(firstNumber);
                int randomIndex = random.nextInt(divisors.size());
                secondNumber = divisors.get(randomIndex);
                String taskStr = String.valueOf(firstNumber) + "/" + String.valueOf(secondNumber) + "=?";
                task = new ExpressionTask(taskStr, String.valueOf(firstNumber / secondNumber));
            } else {
                String taskStr = String.valueOf(firstNumber) + selectedOperation + String.valueOf(secondNumber) + "=?";
                task = new ExpressionTask(taskStr, String.valueOf(calculateSolution(firstNumber, secondNumber, selectedOperation)));
            }
            return task;
        }

        private int calculateSolution(int firstNumber, int secondNumber, char operation) {
            switch (operation) {
                case '+':
                    return firstNumber + secondNumber;
                case '-':
                    return firstNumber - secondNumber;
                case '*':
                    return firstNumber * secondNumber;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operation);
            }
        }

        private static List<Integer> getDivisors(int number) {
            List<Integer> divisors = new ArrayList<>();
            int absNumber = Math.abs(number); // Получаем модуль числа
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
    }
}
