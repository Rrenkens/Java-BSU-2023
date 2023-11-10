package by.MikhailShurov.quizer.tasks.math_tasks;

import by.MikhailShurov.quizer.Result;
import by.MikhailShurov.quizer.Task;

import java.util.*;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask.Operation;

public class EquationTask extends AbstractMathTask {
    String text;
    String answer;

    public EquationTask(
            String text,
            String answer
    ) {
        super(text, answer);
        this.text = text;
        this.answer = answer;
    }

    /**
     * @return 
     */
    @Override
    public String getAnswer() {
        return answer;
    }

    public static class Generator extends AbstractMathTask.Generator {


//        /**
//         * @param minNumber              минимальное число
//         * @param maxNumber              максимальное число
//         * @param EnumSet<Operation> op
//         */
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public EquationTask generate() {
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
            EquationTask task = null;

            if (selectedOperation == '/' && secondNumber == 0) {
                return generate();
            }
            if (xInFirstPosition) {
                if (selectedOperation == '/') {
                    if (maxNumber == 0 && minNumber == 0) {
                        throw new IllegalArgumentException("Invalid lower and upper bounds");
                    }
                    if (firstNumber == 0) {
                        return generate();
                    }
                    // n
                    String taskStr = "x/" + String.valueOf(firstNumber) + "=" + String.valueOf(secondNumber);
                    task = new EquationTask(taskStr, String.valueOf(firstNumber * secondNumber));
                } else {
                    String taskStr = "x" + selectedOperation + secondNumber + "=" + calculateSolution(firstNumber, secondNumber, selectedOperation);
                    // x = firstNumber, secondNumber, solution = <firstNumber><operation><secondNumber>
                    task = new EquationTask(taskStr, String.valueOf(firstNumber));
                }
            } else {
                if (selectedOperation == '/') {
                    List<Integer> divisors = getDivisors(firstNumber);
                    int randomIndex = random.nextInt(divisors.size());
                    int answer = divisors.get(randomIndex);
                    String taskStr = String.valueOf(firstNumber) + selectedOperation + "x=" + String.valueOf(answer);
                    task = new EquationTask(taskStr, String.valueOf(firstNumber / answer));
                } else {
                    String taskStr = String.valueOf(firstNumber) + selectedOperation + "x=" + calculateSolution(firstNumber, secondNumber, selectedOperation);
                    // firstNumber, x = secondNumber, solution = <firstNumber><operation><secondNumber>
                    task = new EquationTask(taskStr, String.valueOf(secondNumber));
                }
            }
            return task;
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
