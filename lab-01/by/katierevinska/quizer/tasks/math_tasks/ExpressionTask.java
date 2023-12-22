package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.exceptions.NoOperationsAllowedException;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ExpressionTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
        private final int precision;

        private MathTask.Operation[] allowedOperations;

        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<Operation> operations

        ) {
            this.precision = precision;
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            allowedOperations = new MathTask.Operation[operations.size()];
            int pos = 0;
            for (var operation : operations) {
                allowedOperations[pos++] = operation;
            }
        }

        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            this(minNumber, maxNumber, 0, operations);
        }

        public ExpressionTask generate() {
            if (minNumber > maxNumber) {
                throw new NoOperationsAllowedException("minNumber shouldn't be more than maxNumber");
            }
            if (allowedOperations.length == 0) {
                throw new NoOperationsAllowedException("should be allowed operations ");
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(0, allowedOperations.length);
            Double num1 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            Double num2 = generatingDoubleWithPrecision(minNumber, maxNumber, precision);
            double answer = 0;
            if (allowedOperations[randomIndex] == Operation.SUM) {
                answer = num1 + num2;
            } else if (allowedOperations[randomIndex] == Operation.DIFFERENCE) {
                answer = num1 - num2;
            } else if (allowedOperations[randomIndex] == Operation.MULTIPLICATION) {
                answer = num1 * num2;
            } else if (allowedOperations[randomIndex] == Operation.DIVISION) {
                if (Objects.equals(num2, 0.0)) {
                    num2 = generationWithout0(minNumber, maxNumber, precision);
                }
                answer = num1 / num2;
            }
            String expression = buildExpression(allowedOperations[randomIndex], String.valueOf(num1), formationWithBracket(num2), "?");
            return new ExpressionTask(expression, String.valueOf(answer), precision);
        }
    }
        public ExpressionTask(
                String text,
                String answer,
                int precision
        ) {
            this.text = text;
            this.answer = answer;
            this.precision = precision;
        }
    }


