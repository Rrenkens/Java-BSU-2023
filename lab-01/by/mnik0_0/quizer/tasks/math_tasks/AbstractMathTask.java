package by.mnik0_0.quizer.tasks.math_tasks;

import by.mnik0_0.quizer.Result;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.EnumSet;
import java.util.Locale;

public class AbstractMathTask implements MathTask {
    public abstract static class Generator implements MathTask.Generator {

        protected double minNumber;
        protected double maxNumber;
        protected EnumSet<Operation> operations;
        protected int precision;

        DecimalFormat decimalFormat;
        DecimalFormatSymbols decimalFormatSymbols;

        Generator(
                double minNumber,
                double maxNumber,
                EnumSet<MathTask.Operation> operations
        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
            this.precision = 0;

            decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormat = new DecimalFormat("#." + "0".repeat(precision), decimalFormatSymbols);
        }

        Generator(
                double minNumber,
                double maxNumber,
                EnumSet<MathTask.Operation> operations,
                int precision
        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
            this.precision = precision;

            decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            decimalFormatSymbols.setDecimalSeparator('.');
            decimalFormat = new DecimalFormat("#." + "0".repeat(precision), decimalFormatSymbols);
        }

        @Override
        public abstract MathTask generate();

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }
    }

    String text;
    double answer;

    public AbstractMathTask(
            String text,
            double answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        System.out.println(answer);
        return text;
    }

    @Override
    public Result validate(String answer) {

        if (this.answer - Double.parseDouble(answer) < 0.1) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
