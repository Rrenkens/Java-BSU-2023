package by.Katya841.quizer.tasks.math_tasks;

import by.Katya841.quizer.Operation;
import by.Katya841.quizer.Rand;
import by.Katya841.quizer.Result;
import by.Katya841.quizer.exceptions.IncorrectRange;
import by.Katya841.quizer.tasks.AbstractMathTask;

import java.util.EnumSet;


public class ExpressionMathTask extends AbstractMathTask {
    private int num1;
    private int num2;
    private Operation operation;
    private String znak;
    public ExpressionMathTask(int num1, int num2, Operation operation) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
        if (operation == Operation.Sum) {
            znak = "+";
        } else if (operation == Operation.Difference) {
            znak = "-";
        } else if (operation == Operation.Multiplication) {
            znak = "*";
        } else {
            znak = "/";
        }
        solveExpression();
    }
    private void solveExpression() {
        if (operation == Operation.Sum) {
            answer = num1 + num2;
        } else if (operation == Operation.Difference) {
            answer = num1 - num2;
        } else if (operation == Operation.Multiplication) {
            answer = num1 * num2;
        } else {
            answer = num1 / num2;
        }
    }

    @Override
    public String getText() {
        StringBuilder text = new StringBuilder();
        text.append(String.valueOf(num1) + " ");
        text.append(znak + " ");
        text.append(String.valueOf(num2) + " ");
        text.append("= ");
        return text.toString();
    }

    @Override
    public Result validate(String answer) {
        return super.validate(answer);
    }
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int min, int max, EnumSet<Operation> operations){
            super(min, max, operations);
        }

        @Override
        public ExpressionMathTask generate() {
            int num1 = Rand.generateNumber(getMinNumber(), getMaxNumber());
            int num2 = Rand.generateNumber(getMinNumber(), getMaxNumber());
            Operation operation = listOperations.get(Rand.generateNumber(0, listOperations.size() - 1));

            while (operation == Operation.Division && num2 == 0) {
                if (getMaxNumber() == getMinNumber() && getMaxNumber() == 0) {
                    throw new IncorrectRange("Incorrect min and max in ExpressionMathTask.Generator");
                }
                num2 = Rand.generateNumber(getMinNumber(), getMaxNumber());
            }
            if (operation == Operation.Division && num1 % num2 != 0) {
                num1 *= num2;
            }

            return new ExpressionMathTask(num1, num2, operation);
        }

    }
}
