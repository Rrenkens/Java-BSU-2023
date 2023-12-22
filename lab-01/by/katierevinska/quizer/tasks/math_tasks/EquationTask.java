package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;


import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
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

            this.allowedOperations = new MathTask.Operation[operations.size()];
            int pos = 0;
            for (var operation : operations) {
                allowedOperations[pos++] = operation;
            }
        }

        public EquationTask generate() {
            if (minNumber > maxNumber) {
                throw new NoOperationsAllowedException("minNumber shouldn't be more than maxNumber");
            }
            if (allowedOperations.length == 0) {
                throw new NoOperationsAllowedException("should be allowed operations ");
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);
            Double num1 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            Double num2 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            int viewOfEquation = ThreadLocalRandom.current().nextInt(0, 2);
            double answer = 0;
            if (allowedOperations[randomIndex] == MathTask.Operation.SUM) {
                answer = num2 - num1;
            } else if (allowedOperations[randomIndex] == MathTask.Operation.DIFFERENCE) {
                if (viewOfEquation == 0) {
                    answer = num2 + num1;
                } else {
                    answer = num1 - num2;
                }
            } else if (allowedOperations[randomIndex] == MathTask.Operation.MULTIPLICATION) {
                if (Objects.equals(num1, 0.0)) {
                    num1 = generationWithout0(minNumber, maxNumber, precision);
                    System.out.println(num1);
                }
                answer = num2 / num1;
            } else if (allowedOperations[randomIndex] == MathTask.Operation.DIVISION) {
                if (Objects.equals(num1, 0.0)) {
                    num1 = generationWithout0(minNumber, maxNumber, precision);
                    System.out.println(num1);
                } else {
                    System.out.println(num1);
                }
                if (viewOfEquation == 0) {
                    answer = num1 * num2;
                } else {
                    if (Objects.equals(num2, 0.0)) {
                        num2 = generationWithout0(minNumber, maxNumber, precision);
                        System.out.println(num2);
                    }
                    answer = num1 / num2;
                }
            }
            String expression = viewOfEquation == 0 ?
                    buildExpression(allowedOperations[randomIndex], "x", formationWithBracket(num1), String.valueOf(num2))
                    : buildExpression(allowedOperations[randomIndex], String.valueOf(num1), "x", String.valueOf(num2));
            return new EquationTask(expression, String.valueOf(answer), precision);
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