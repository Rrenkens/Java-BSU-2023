package by.Lenson423.quizer.tasks.math_tasks;

import by.Lenson423.quizer.Operation;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(double num1, Operation operation, double num2, int precision) {
        this.precision = precision;
        DecimalFormat decimalFormat = new DecimalFormat("#." + "#".repeat(precision));
        switch (operation) {
            case SUM -> {
                this.expression = decimalFormat.format(num1) + "+" + decimalFormat.format(num2) + "=?";
                result = num1 + num2;
            }
            case DIFFERENCE -> {
                this.expression = decimalFormat.format(num1) + "-" + decimalFormat.format(num2) + "=?";
                result = num1 - num2;
            }
            case MULTIPLICATION -> {
                this.expression = decimalFormat.format(num1) + "*" + decimalFormat.format(num2) + "=?";
                result = num1 * num2;
            }
            case DIVISION -> {
                if (num2 == 0) {
                    throw new ArithmeticException("Divisible by 0");
                }
                this.expression = decimalFormat.format(num1) + "/" + decimalFormat.format(num2) + "=?";
                result = num1 / num2;
            }
        }
        result *= Math.pow(10, precision);
    }

    public static class Generator extends AbstractMathTask.Generator<ExpressionTask> {
        @Override
        protected ExpressionTask returnRandom() {
            return new ExpressionTask(ThreadLocalRandom.current().nextDouble(minNumber, maxNumber),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(minNumber, maxNumber), precision);
        }

        @Override
        protected ExpressionTask returnPositive() {
            return new ExpressionTask(ThreadLocalRandom.current().nextDouble(1, maxNumber),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(1, maxNumber), precision);
        }

        @Override
        protected ExpressionTask returnNegative() {
            return new ExpressionTask(ThreadLocalRandom.current().nextDouble(minNumber, 0),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(minNumber, 0), precision);
        }

        /**
         * @param minNumber  минимальное число
         * @param maxNumber  максимальное число
         * @param operations разрешить генерацию с операторами из массива
         */
        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations
        ) {
            super(minNumber, maxNumber, operations);
        }

        public Generator(
                int minNumber,
                int maxNumber,
                EnumSet<Operation> operations,
                int precision
        ) {
            super(minNumber, maxNumber, operations, precision);
        }
    }
}
