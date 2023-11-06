package by.lamposhka.quizer.tasks.math_tasks;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.Random;


public abstract class AbstractMathTask implements MathTask {
    private final String text;
    private final double answer;
    private final int precision;

    protected AbstractMathTask(
            String text,
            double answer
    ) {
        this.text = text;
        this.answer = answer;
        this.precision = 0;
    }

    protected AbstractMathTask(
            String text,
            double answer,
            int precision
    ) {
        this.text = text;
        this.answer = answer;
        this.precision = precision;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        double doubleAnswer;
        try {
            doubleAnswer = Double.parseDouble(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        if (Math.abs(this.answer - doubleAnswer) < Math.pow(10, -1 * precision)) { //org.apache.commons.math3.util.Precision
            return Result.OK;
        }
        return Result.WRONG;
    }

    public static abstract class Generator implements MathTask.Generator {
        private final ArrayList<Operation> operators = new ArrayList<>(4);
        private final double minNumber;
        private final double maxNumber;
        private final Random random = new Random();
        protected final int precision;

        protected Generator(double minNumber,
                            double maxNumber,
                            int precision,
                            EnumSet<Operation> validOperations) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
            if (validOperations.contains(MathTask.Operation.SUM)) {
                operators.add(MathTask.Operation.SUM);
            }
            if (validOperations.contains(MathTask.Operation.DIFFERENCE)) {
                operators.add(MathTask.Operation.DIFFERENCE);
            }
            if (validOperations.contains(MathTask.Operation.MULTIPLICATION)) {
                operators.add(MathTask.Operation.MULTIPLICATION);
            }
            if (validOperations.contains(MathTask.Operation.DIVISION)) {
                operators.add(MathTask.Operation.DIVISION);
            }
        }

        protected double generateNum() {
            return Math.floor((random.nextDouble(maxNumber) + minNumber) * Math.pow(10, precision)) / Math.pow(10, precision);
        }

        protected MathTask.Operation generateOperator() {
            return operators.get(random.nextInt(operators.size()));
        }

        protected boolean generateVariablePositionIndicator() {
            return random.nextBoolean();
        }

        protected double castToPrecision(double num) {
            return Math.floor(num * Math.pow(10, precision)) / Math.pow(10, precision);
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        @Override
        public abstract AbstractMathTask generate();

    }

}
