package by.Lenson423.quizer.tasks.math_tasks;

import by.Lenson423.quizer.Result;
import by.Lenson423.quizer.exceptions.CantCreateTaskWithoutOperations;
import by.Lenson423.quizer.exceptions.CantGenerateTask;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMathTask implements MathTask {
    protected String expression;
    protected double result;

    protected int precision;

    @Override
    public String getText() {
        return expression + " P.S. Dull the answer, using maximum precision from the task ";
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
        double tmp;
        try {
            tmp = Double.parseDouble(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
        int diff = (int) (tmp * Math.pow(10, precision) - result);
        return (diff == 0) ? Result.OK : Result.WRONG;
    }

    static abstract class Generator<T extends AbstractMathTask> implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;

        protected List<Operation> operations = new LinkedList<>();
        int precision;

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        abstract T returnRandom();

        abstract T returnPositive();

        abstract T returnNegative();

        /**
         * @param minNumber  минимальное число
         * @param maxNumber  максимальное число
         * @param operations разрешить генерацию с операторами из массива
         */
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations,
                int precision
        ) {
            if (minNumber > maxNumber) {
                throw new IllegalArgumentException("Min value > Max value");
            }
            if (operations == null) {
                throw new IllegalArgumentException("Set of operations is null");
            }
            if (operations.isEmpty()) {
                throw new CantCreateTaskWithoutOperations("Empty operations set");
            }
            this.operations.addAll(operations);
            if (precision < 0) {
                throw new IllegalArgumentException("precision < 0");
            }
            this.precision = precision;
            this.minNumber = roundAvoid(minNumber);
            this.maxNumber = roundAvoid(maxNumber);

        }

        public double roundAvoid(double value) {
            double scale = Math.pow(10, precision);
            return Math.round(value * scale) / scale;
        }

        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> operations
        ) {
            this(minNumber, maxNumber, operations, 0);
        }

        /**
         * return задание типа T
         */
        public T generate() throws CantGenerateTask {
            try {
                return returnRandom();
            } catch (Exception ignored) {
            }
            if (minNumber == maxNumber & minNumber == 0
                    & !(operations.contains(Operation.SUM) | operations.contains(Operation.DIFFERENCE))) {
                throw new CantGenerateTask("Can't generate Task");
            }
            try {
                return returnPositive();
            } catch (Exception ignored) {
            }
            return returnNegative();
        }
    }
}
