package by.DashaGnedko.quizer.tasks.math_tasks;

import by.DashaGnedko.quizer.Result;
import by.DashaGnedko.quizer.tasks.Task;

import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {
    abstract public String getText();

    abstract public Result validate(String answer);

    static abstract class Generator implements MathTask.Generator {
        protected double minNumber;
        protected double maxNumber;
        protected int precision;
        protected EnumSet<Operation> operations;
        protected Random random = new Random();

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
        @Override
        abstract public Task generate();

        Generator(
                int minNumber,
                int maxNumber,
                int precision,
                EnumSet<Operation> operations
        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            if (minNumber > maxNumber) {
                throw new IllegalArgumentException("minNumber > maxNumber");
            }
            if (precision < 0 || precision > 10) {
                throw new IllegalArgumentException("minNumber > maxNumber");
            }
            this.operations = operations;
        }
    }
}
