package by.Lenson423.quizer.tasks.math_tasks;

import by.Lenson423.quizer.Operation;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;


public class EquationTask extends AbstractMathTask {
    public EquationTask(double num1, Operation operation, double answer, int precision) {
        if (precision < 0){
            throw new IllegalArgumentException("Invalid precision");
        }
        this.precision = precision;
        int leftOrRight = ThreadLocalRandom.current().nextInt(0, 2);
        DecimalFormat decimalFormat;
        if (precision > 0) {
            decimalFormat = new DecimalFormat("#." + "#".repeat(precision));
        } else {
            decimalFormat = new DecimalFormat("#");
        }

        if (leftOrRight == 0) {
            switch (operation) {
                case SUM -> {
                    this.expression = "x+" + decimalFormat.format(num1) + "=" + decimalFormat.format(answer);
                    result = answer - num1;
                }
                case DIFFERENCE -> {
                    this.expression = "x-" + decimalFormat.format(num1) + "=" + decimalFormat.format(answer);
                    result = answer + num1;
                }
                case MULTIPLICATION -> {
                    if (num1 == 0 & answer != 0) {
                        throw new ArithmeticException("No roots");
                    }
                    if (num1 == 0 & answer == 0) {
                        throw new ArithmeticException("inf count of roots");
                    }
                    this.expression = "x*" + decimalFormat.format(num1) + "=" + decimalFormat.format(answer);
                    result = answer / num1;
                }
                case DIVISION -> {
                    if (num1 == 0) {
                        throw new ArithmeticException("Division by 0");
                    }
                    this.expression = "x/" + decimalFormat.format(num1) + "=" + decimalFormat.format(answer);
                    result = answer * num1;
                }
            }
        } else if (leftOrRight == 1) {
            switch (operation) {
                case SUM -> {
                    this.expression = decimalFormat.format(num1) + "+x=" + decimalFormat.format(answer);
                    result = answer - num1;
                }
                case DIFFERENCE -> {
                    this.expression = decimalFormat.format(num1) + "-x=" + decimalFormat.format(answer);
                    result = num1 - answer;
                }
                case MULTIPLICATION -> {
                    if (num1 == 0 & answer != 0) {
                        throw new ArithmeticException("No roots");
                    }
                    if (num1 == 0 & answer == 0) {
                        throw new ArithmeticException("inf count of roots");
                    }
                    this.expression = decimalFormat.format(num1) + "*x=" + decimalFormat.format(answer);
                    result = answer / num1;
                }
                case DIVISION -> {
                    if (answer == 0 & num1 != 0) {
                        throw new ArithmeticException("No roots");
                    }
                    this.expression = decimalFormat.format(num1) + "/x=" + decimalFormat.format(answer);
                    result = num1 / answer;
                }
            }
        }
        result *= Math.pow(10, precision);
    }

    public static class Generator extends AbstractMathTask.Generator<EquationTask> {
        @Override
        EquationTask returnRandom() {
            return new EquationTask(ThreadLocalRandom.current().nextDouble(minNumber, maxNumber),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(minNumber, maxNumber), precision);
        }

        @Override
        protected EquationTask returnPositive() {
            return new EquationTask(ThreadLocalRandom.current().nextDouble(1, maxNumber),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(1, maxNumber), precision);
        }

        @Override
        protected EquationTask returnNegative() {
            return new EquationTask(ThreadLocalRandom.current().nextDouble(minNumber, 0),
                    operations.get(ThreadLocalRandom.current().nextInt(0, operations.size())),
                    ThreadLocalRandom.current().nextDouble(minNumber, 0), precision);
        }

        /**
         * @param minNumber  минимальное число
         * @param maxNumber  максимальное число
         * @param operations разрешить генерацию с операторами из массива
         */
        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
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

