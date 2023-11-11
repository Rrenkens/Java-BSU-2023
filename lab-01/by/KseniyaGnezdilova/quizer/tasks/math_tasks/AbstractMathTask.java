package by.KseniyaGnezdilova.quizer.tasks.math_tasks;

public abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator{
        protected int precision;
        protected double minNumber;
        protected double maxNumber;
        protected boolean generateSum;
        protected boolean generateDifference;
        protected boolean generateMultiplication;
        protected boolean generateDivision;

        public Generator(
                int precision,
                double minNumber,
                double maxNumber,
                boolean generateSum,
                boolean generateDifference,
                boolean generateMultiplication,
                boolean generateDivision
        ) {
            this.precision = precision;
            this.maxNumber = maxNumber;
            this.minNumber = minNumber;
            this.generateSum = generateSum;
            this.generateDifference = generateDifference;
            this.generateMultiplication = generateMultiplication;
            this.generateDivision = generateDivision;
        }

        public double getMaxNumber(){
            return maxNumber;
        }

        public double getMinNumber(){
            return minNumber;
        }
    }
}
