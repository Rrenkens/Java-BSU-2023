package by.rycbaryana.quizer.tasks.math_tasks;

import by.rycbaryana.quizer.tasks.Task;

import java.util.EnumSet;
import java.util.Random;

interface MathTask extends Task {
    interface Generator extends Task.Generator {
        double getMinNumber();

        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}

abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator {
        double min;
        double max;
        int precision = 0;
        final Random random = new Random();
        EnumSet<Operation> allowed;

        Generator(EnumSet<Operation> allowed, double min, double max) throws IllegalArgumentException {
            this.allowed = allowed;
            this.min = min;
            this.max = max;
            if (min > max) {
                throw new IllegalArgumentException("Min > max");
            }
            if (allowed.isEmpty()) {
                throw  new IllegalArgumentException("No allowed operations");
            }
        }

        Generator(EnumSet<Operation> allowed, double min, double max, int precision) {
            this.allowed = allowed;
            this.min = min;
            this.max = max;
            this.precision = precision;
            if (min > max) {
                throw new IllegalArgumentException("Min > max");
            }
            if (allowed.isEmpty()) {
                throw  new IllegalArgumentException("No allowed operations");
            }
            if (precision <= 0) {
                throw new IllegalArgumentException("Precision must be a positive number");
            }
        }

        @Override
        abstract public AbstractMathTask generate();

        @Override
        public double getMaxNumber() {
            return max;
        }

        public double getMinNumber() {
            return min;
        }

        public double generateNumber() {
            if (precision == 0) {
                return random.nextInt((int)min, (int) max + 1);
            } else {
                return random.nextDouble(min, max);
            }
        }

        Operation chooseOperation() {
            return allowed.stream().toList().get(random.nextInt(0, allowed.size()));
        }
    }

    double applyOperation(double lhs, double rhs, Operation operation) {
        return switch (operation) {
            case SUM -> {
                yield lhs + rhs;
            }
            case SUB -> {
                yield lhs - rhs;
            }
            case DIV -> {
                yield lhs / rhs;
            }
            case MULT -> {
                yield lhs * rhs;
            }
        };
    }
}
