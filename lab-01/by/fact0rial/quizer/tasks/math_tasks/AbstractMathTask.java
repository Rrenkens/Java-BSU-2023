package by.fact0rial.quizer.tasks.math_tasks;

import by.fact0rial.quizer.Result;
import by.fact0rial.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.math.MathContext;

abstract public class AbstractMathTask implements MathTask {
    final private double ans;
    final private String text;
    AbstractMathTask(String text, double answer) {
        this.ans = answer;
        this.text = text;
    }
    @Override
    public String getText() {
        return text;
    }
    static abstract class Generator implements MathTask.Generator {
        final protected int numbers;
        final protected double minNumber;
        final protected double maxNumber;
        final protected EnumSet<Operation> operations;
        @Override
        public double getMinNumber() {
            return this.minNumber;
        }
        @Override
        public double getMaxNumber() {
            return this.maxNumber;
        }
        @Override
        public double getDiffNumber() {
            return this.maxNumber - this.minNumber;
        }
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("max number should not be less than min number");
            }
            if (operations.isEmpty()) {
                throw new IllegalArgumentException("possible operations list should not be empty");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
            numbers = 0;
        }
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations, int context) {
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("max number should not be less than min number");
            }
            if (operations.isEmpty()) {
                throw new IllegalArgumentException("possible operations list should not be empty");
            }
            if (context < 0) {
                throw new IllegalArgumentException("precision should be not negative");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
            numbers = context;
        }
    }
    @Override
    public Result validate(String answer) {
        try {
            double ans = Double.parseDouble(answer);
            if (ans == this.ans) {
                return Result.OK;
            }
            return Result.WRONG;
        } catch(NumberFormatException ex) {
            return Result.INCORRECT_INPUT;
        }
    }
}
