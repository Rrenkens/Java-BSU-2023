package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;


import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
        private final double minNumber;
        private final double maxNumber;
        private final int precision;

        private MathTask.Operation[] allowedOperations;

        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            this(minNumber, maxNumber, 0, operations);
        }

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            this.precision = precision;
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;

            int numberOfAllowedOperations = 0;
            if (operations.contains(MathTask.Operation.Sum)) {
                numberOfAllowedOperations++;
            }
            if (operations.contains(MathTask.Operation.Difference)) {
                numberOfAllowedOperations++;
            }
            if (operations.contains(MathTask.Operation.Multiplication)) {
                numberOfAllowedOperations++;
            }
            if (operations.contains(MathTask.Operation.Division)) {
                numberOfAllowedOperations++;
            }
            allowedOperations = new MathTask.Operation[numberOfAllowedOperations];
            int pos = 0;
            if (operations.contains(MathTask.Operation.Sum)) {
                allowedOperations[pos++] = MathTask.Operation.Sum;
            }
            if (operations.contains(MathTask.Operation.Difference)) {
                allowedOperations[pos++] = MathTask.Operation.Difference;
            }
            if (operations.contains(MathTask.Operation.Multiplication)) {
                allowedOperations[pos++] = MathTask.Operation.Multiplication;
            }
            if (operations.contains(MathTask.Operation.Division)) {
                allowedOperations[pos] = MathTask.Operation.Division;
            }

        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        public EquationTask generate() {
            if (minNumber > maxNumber) {
                throw new NoOperationsAllowedException("minNumber shouldn't be more than maxNumber");
            }
            if (allowedOperations.length == 0) {
                throw new NoOperationsAllowedException("should be allowed operations ");
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);
            StringBuilder expression = new StringBuilder();
            double answer = 0;
            double num1 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            double num2 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            double viewOfEquation = ThreadLocalRandom.current().nextDouble(0, 2);
            if (viewOfEquation == 0) {
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append("x+")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = num2 - num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append("x-")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = num2 + num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if (Objects.equals(num1, 0.0)) {
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append("x*")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = num2 / num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if (Objects.equals(num1, 0.0)) {
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                    }
                    expression.append("x/")
                            .append(formationWithBracket(num1))
                            .append("=")
                            .append(num2);
                    answer = num1 * num2;
                }
            } else {
                if (allowedOperations[randomNum] == MathTask.Operation.Sum) {
                    expression.append(num1)
                            .append("+x=")
                            .append(num2);
                    answer = num2 - num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Difference) {
                    expression.append(num1)
                            .append("-x=")
                            .append(num2);
                    answer = num1 - num2;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Multiplication) {
                    if (Objects.equals(num1, 0.0)) {
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                        System.out.println(num1);
                    }
                    expression.append(num1)
                            .append("*x=")
                            .append(num2);
                    answer = num2 / num1;
                } else if (allowedOperations[randomNum] == MathTask.Operation.Division) {
                    if (Objects.equals(num1, 0.0)) {
                        num1 = generationWithout0(minNumber, maxNumber, precision);
                        System.out.println(num1);
                    }
                    if (Objects.equals(num2, 0.0)) {
                        num2 = generationWithout0(minNumber, maxNumber, precision);
                        System.out.println(num2);
                    }
                    expression.append(num1)
                            .append("/x=")
                            .append(num2);
                    answer = num1 / num2;
                }
            }
            return new EquationTask(expression.toString(), String.valueOf(answer), precision);
        }
    }

    public EquationTask(
            String text,
            String answer,
            int precision
    ) {
        this.text = text;
        this.answer = answer;
        this.precision = precision;
    }

}