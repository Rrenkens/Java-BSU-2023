package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator{
        protected int precision;
        protected double minNumber;
        protected double maxNumber;
        protected EnumSet<Operations> operations;

        public Generator(
                int precision,
                double minNumber,
                double maxNumber,
                EnumSet<Operations> operations
        ) {
            this.precision = precision;
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            this.operations = operations;
        }

        public double getMaxNumber(){
            return maxNumber;
        }

        public double getMinNumber(){
            return minNumber;
        }
    }
}
