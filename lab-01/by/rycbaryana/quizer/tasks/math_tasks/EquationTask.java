package by.rycbaryana.quizer.tasks.math_tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;

import java.text.DecimalFormat;
import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    double num;
    double ans;
    Operation operation;
    boolean isFirstOperand;
    int precision;

    EquationTask(double num, double ans, Operation operation, boolean isFirstOperand, int precision) {
        this.num = num;
        this.ans = ans;
        this.operation = operation;
        this.isFirstOperand = isFirstOperand;
        this.precision = precision;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(EnumSet<Operation> allowed, double min, double max) {
            super(allowed, min, max);
        }

        public Generator(EnumSet<Operation> allowed, double min, double max, int precision) {
            super(allowed, min, max, precision);
        }

        @Override
        public EquationTask generate() {
            double num = generateNumber();
            double ans = generateNumber();
            Operation operation = chooseOperation();
            boolean isFirst = random.nextBoolean();
            while (operation == Operation.DIV && !isFirst && (num == 0 || ans == 0)) {
                num = generateNumber();
            }
            if (precision == 0 && operation == Operation.MULT ) {
                if (ans % num != 0) {
                    ans *= num;
                } else if (num % ans != 0) {
                    num *= ans;
                }
            }
            if (precision == 0 && operation == Operation.DIV && !isFirst && num % ans != 0) {
                num *= ans;
            }
            return new EquationTask(num, ans, operation, isFirst, precision);
        }
    }

    @Override
    public String getText() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        String n = num >= 0 ? df.format(num) : "(" + df.format(num) + ")";
        if (isFirstOperand) {
            return String.format("x %s %s = %s", operation.toString(), n, df.format(ans));
        } else {
            return String.format("%s %s x = %s", n, operation.toString(), df.format(ans));
        }
    }

    @Override
    public Result validate(Answer answer) {
        if (!answer.isNumeric()) {
            return Result.INCORRECT_INPUT;
        }
        double res;
        if (isFirstOperand) {
            res = applyOperation(answer.getNum(), num, operation);
        } else {
            res = applyOperation(num, answer.getNum(), operation);
        }
        double eps = precision == 0 ? 1e-8 : Math.pow(10, -precision);
        if (Math.abs(res - ans) < eps) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
