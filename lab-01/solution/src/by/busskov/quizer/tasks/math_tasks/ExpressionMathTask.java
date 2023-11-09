package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.exceptions.InvalidConditionException;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> availableOperations
        ) {
            super(minNumber, maxNumber, availableOperations);
            if (minNumber == 0
            && maxNumber == 0
            && availableOperations.size() == 1
            && availableOperations.contains(Operation.DIVISION)) {
                throw new IllegalArgumentException("Not possible to generate a valid task");
            }
        }

        @Override
        public ExpressionMathTask generate() {
            Random random = new Random();
            int firstNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
            int secondNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;

            Object[] operations = availableOperations.toArray();
            int randomIndex = random.nextInt(operations.length);
            Operation operation = (Operation) operations[randomIndex];
            String condition = Integer.toString(firstNumber)
                    + switch (operation) {
                case SUM -> '+';
                case DIFFERENCE -> '-';
                case MULTIPLICATION -> '*';
                case DIVISION -> '/';
            } + secondNumber + "=?";
            double answer = switch (operation) {
                case DIFFERENCE -> firstNumber - secondNumber;
                case MULTIPLICATION -> firstNumber * secondNumber;
                case DIVISION -> {
                    if (secondNumber == 0) {
                        throw new InvalidConditionException("Division by 0");
                    }
                    yield (double) firstNumber / secondNumber;
                }
                case SUM -> firstNumber + secondNumber;
            };
            return new ExpressionMathTask(condition, answer, 1e-3);
        }
    }
//TODO remove hardcode precision from return

    public ExpressionMathTask(
            String condition,
            double answer,
            double precision
    ) {
        super(condition, answer, precision);
    }
}
