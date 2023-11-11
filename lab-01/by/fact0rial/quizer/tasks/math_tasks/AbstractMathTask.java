package by.fact0rial.quizer.tasks;

import by.fact0rial.quizer.Result;
import by.fact0rial.quizer.tasks.MathTask;

import java.util.EnumSet;

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
        final protected double minNumber;
        final protected double maxNumber;
        final protected EnumSet<Operation> operations;
        Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
        }
    }
    @Override
    public Result validate(String answer) {
        double ans = Double.parseDouble(answer);
        if (ans == this.ans) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
