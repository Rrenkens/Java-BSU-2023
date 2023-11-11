package by.DashaGnedko.quizer.tasks.math_tasks;

import by.DashaGnedko.quizer.Result;
import by.DashaGnedko.quizer.tasks.Task;

import java.text.DecimalFormat;
import java.util.EnumSet;

public class ExpressionMathTask extends AbstractMathTask {
    private double num1;
    private double num2;
    private Operation operator;
    private int precision;


    public ExpressionMathTask(double num1, Operation operator, double num2, int precision) {
        this.num1 = number(num1);
        this.operator = operator;
        this.num2 = number(num2);
        this.precision = precision;
    }

    String stringNumber(double num) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        if (num >= 0) {
            return df.format(num);
        }
        return "(" + df.format(num) + ")";
    }

    double number(double num) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        return Double.parseDouble(df.format(num));
    }
    @Override
    public String getText() {
        return stringNumber(num1) + operator.toString() + stringNumber(num2) + "=?";
    }

    @Override
    public Result validate(String answer) {
        try {
            Double.parseDouble(answer);
        } catch (Exception e) {
            return Result.INCORRECT_INPUT;
        }

        double result;
        switch (operator) {
            case Operation.SUM -> {
                result = num1 + num2;
            }
            case Operation.DIFFERENCE -> {
                result = num1 - num2;
            }
            case Operation.MULTIPLICATION -> {
                result = num1 * num2;
            }
            default -> {
                result = num1 / num2;
            }
        }
        if (precision > 0) {
            return (Math.abs(Double.parseDouble(answer) - result) < 2 * Math.pow(0.1, precision) ? Result.OK : Result.WRONG);
        } else {
            return ((int) Double.parseDouble(answer) == (int) result ? Result.OK : Result.WRONG);
        }
    }

    public static class Generator extends AbstractMathTask.Generator {

        public Generator(int minNumber, int maxNumber, int precision, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, precision, operations);
        }

        boolean incorrect(ExpressionMathTask task) {
            if (precision > 0) {
                return (task.operator == Operation.DIVISION && task.num2 == 0);
            }
            return (task.operator == Operation.DIVISION && (int)task.num2 == 0);
        }

        Task generateTask() {
            double num1 = this.random.nextDouble(this.minNumber, this.maxNumber);
            double num2 = this.random.nextDouble(this.minNumber, this.maxNumber);
            Operation operation = operations.stream().toList().get(this.random.nextInt(0, operations.size()));
            return (Task) new ExpressionMathTask(num1, operation, num2, precision);
        }

        @Override
        public Task generate() {
            Task task = generateTask();
            while (incorrect((ExpressionMathTask) task)) {
                task = generateTask();
            }
            return task;
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
    }
}