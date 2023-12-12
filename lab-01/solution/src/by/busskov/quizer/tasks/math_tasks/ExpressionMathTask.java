package by.busskov.quizer.tasks.math_tasks;

import by.busskov.quizer.exceptions.InvalidConditionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> availableOperations,
                int precision
        ) {
            super(minNumber, maxNumber, availableOperations, precision);
            if (Math.abs(minNumber) < Math.pow(10, -precision)
            && Math.abs(maxNumber) < Math.pow(10, -precision)
            && availableOperations.size() == 1
            && availableOperations.contains(Operation.DIVISION)) {
                throw new IllegalArgumentException("Not possible to generate a valid task");
            }
        }

        @Override
        public ExpressionMathTask generate() {
            Random random = new Random();

            double rawFirstNumber = random.nextDouble() * getDiffNumber() + minNumber;
            double rawSecondNumber = random.nextDouble() * getDiffNumber() + minNumber;

            BigDecimal firstNumber = new BigDecimal(rawFirstNumber).setScale(precision, RoundingMode.DOWN);
            BigDecimal secondNumber = new BigDecimal(rawSecondNumber).setScale(precision, RoundingMode.DOWN);


            Object[] operations = availableOperations.toArray();
            int randomIndex = random.nextInt(operations.length);
            Operation operation = (Operation) operations[randomIndex];
            String condition = firstNumber.toString()
                    + switch (operation) {
                case SUM -> '+';
                case DIFFERENCE -> '-';
                case MULTIPLICATION -> '*';
                case DIVISION -> '/';
            } + secondNumber + "=?";

            double answer = getAnswer(firstNumber, secondNumber, operation);
            return new ExpressionMathTask(condition, answer, Math.pow(10, -precision) / 2);
        }
    }

    private static double getAnswer(BigDecimal firstNumber, BigDecimal secondNumber, Operation operation) {
        double first = firstNumber.doubleValue();
        double second = secondNumber.doubleValue();
        return switch (operation) {
            case DIFFERENCE -> first - second;
            case MULTIPLICATION -> first * second;
            case DIVISION -> {
                if (second == 0) {
                    throw new InvalidConditionException("Division by 0");
                }
                yield first / second;
            }
            case SUM -> first + second;
        };
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
