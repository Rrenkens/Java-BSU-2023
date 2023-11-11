package by.DashaGnedko.quizer.tasks.math_tasks;

import by.DashaGnedko.quizer.Result;
import by.DashaGnedko.quizer.tasks.Task;

import java.util.EnumSet;
import java.text.DecimalFormat;

public class EquationMathTask extends AbstractMathTask {
    private double num;
    private Operation operator;
    private double ans;
    private int type;
    private int precision;


    public EquationMathTask(double num, Operation operator, double ans, int type, int precision) {
        this.num = number(num);
        this.operator = operator;
        this.ans = number(ans);
        this.type = type;
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
        return "x" + operator.toString() + stringNumber(num) + "=" + stringNumber(ans);
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
                result = ans - num;
                if (precision == 0) {
                    result = (int) ans - (int) num;
                }
            }
            case Operation.DIFFERENCE -> {
                result = (type == 0 ? num - ans : num + ans);
                if (precision == 0) {
                    result = (type == 0 ? (int) num - (int) ans : (int) num + (int) ans);
                }
            }
            case Operation.MULTIPLICATION -> {
                result = ans / num;
                if (precision == 0) {
                    result = (int) ans / (int) num;
                }
            }
            default -> {
                result = (type == 0 ? num / ans : ans * num);
                if (precision == 0) {
                    result = (type == 0 ? (int) num / (int) ans : (int) ans * (int)  num);
                }
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

        boolean incorrect(EquationMathTask task) {
            double cast_num = (precision > 0 ? task.num : (int)task.num);
            double cast_ans = (precision > 0 ? task.ans : (int)task.ans);
            if (task.operator == Operation.DIVISION) {
                if (task.type == 0 && cast_num == 0 && cast_ans != 0) {
                    return true;
                }
                if (task.type == 0 && cast_num != 0 && cast_ans == 0) {
                    return true;
                }
                if (task.type == 1 && (cast_num == 0 || cast_ans == 0)) {
                    return true;
                }
            }
            if (task.operator == Operation.SUM && cast_num == 0 && cast_ans != 0) {
                    return true;
            }
            return false;
        }

        Task generateTask() {
            double num = this.random.nextDouble(this.minNumber, this.maxNumber);
            Operation operation = operations.stream().toList().get(this.random.nextInt(0, operations.size()));
            double answer = this.random.nextDouble(this.minNumber, this.maxNumber);
            int type = this.random.nextInt(0, 2);
            return (Task) new EquationMathTask(num, operation, answer, type, precision);
        }

        @Override
        public Task generate() {
            Task task = generateTask();
            while (incorrect((EquationMathTask) task)) {
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
