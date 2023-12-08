package by.rycbaryana.quizer.tasks.math_tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;

import java.text.DecimalFormat;
import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    double lhs;
    double rhs;
    Operation operation;
    int precision;

    ExpressionTask(double lhs, double rhs, Operation operation, int precision) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operation = operation;
        this.precision = precision;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(EnumSet<Operation> allowed, double min, double max) {
            super(allowed, min, max);
            if (allowed.isEmpty()) {
                throw new IllegalArgumentException("No allowed operations");
            }
        }

        public Generator(EnumSet<Operation> allowed, double min, double max, int precision) {
            super(allowed, min, max, precision);
            if (allowed.isEmpty()) {
                throw new IllegalArgumentException("No allowed operations");
            }
        }

        @Override
        public ExpressionTask generate() {
            double lhs = generateNumber();
            double rhs = generateNumber();
            Operation operation = chooseOperation();
            while (operation == Operation.DIV && rhs == 0) {
                rhs = generateNumber();
            }
            if (precision == 0 && operation == Operation.DIV && lhs % rhs != 0) {
                lhs *= rhs;
            }
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(precision);
            lhs = Double.parseDouble(df.format(lhs));
            rhs = Double.parseDouble(df.format(rhs));
            return new ExpressionTask(lhs, rhs, operation, precision);
        }
    }

    @Override
    public String getText() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        String r = rhs >= 0 ? df.format(rhs) : "(" + df.format(rhs) + ")";
        return String.format("%s %s %s = ?", df.format(lhs), operation.toString(), r);
    }

    @Override
    public Result validate(Answer answer) {
        if (!answer.isNumeric()) {
            return Result.INCORRECT_INPUT;
        }
        double eps = 1e-8;
        if (Math.abs(applyOperation(lhs, rhs, operation) - answer.getNum()) < eps) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
