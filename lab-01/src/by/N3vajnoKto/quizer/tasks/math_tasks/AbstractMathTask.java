package by.N3vajnoKto.quizer.tasks.math_tasks;

import by.N3vajnoKto.quizer.Result;
import by.N3vajnoKto.quizer.Task;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.*;
import java.lang.Math;

public abstract class AbstractMathTask implements Task {
    private double result;
    private int precision;
    String text;

    public enum Operation {
        Add,
        Sub,
        Mul,
        Div
    }

    static public abstract class Generator implements Task.Generator {
        private double min;
        private double max;
        private int precision;
        private EnumSet<Operation> operations;

        protected Generator(double min, double max, int prec, EnumSet<Operation> st) {
            this.min = min;
            this.max = max;
            this.precision = prec;
            this.operations = st.clone();
        }

        public int precision() {
            return this.precision;
        }

        public String format(double a, int n) {
            if (n < 0) {
                throw new InvalidParameterException("precision is below zero");
            }
            DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(this.precision);
            if (a > 0) {
                return format.format(a);
            } else {
                return "(" + format.format(a) + ")";
            }
        }

        public AbstractMap.SimpleEntry<double[], Operation> generateExpr() {
            if (this.operations.isEmpty()) {
                throw new InvalidParameterException("no operation to generate");
            }

            Random random = new Random();

            int ind = Math.abs(random.nextInt()) % this.operations.size();

            Operation op = this.operations.stream().toList().get(ind);

            double a, b, c = 0;

            a = Generator.round(random.nextDouble(this.min, this.max), this.precision);
            b = Generator.round(random.nextDouble(this.min, this.max), this.precision);

            if (op == Operation.Div || op == Operation.Mul) {
                a = Generator.round(random.nextDouble(this.min, this.max), this.precision);
                b = Generator.round(random.nextDouble(this.min, this.max), this.precision);

                while (b == 0) {
                    b = Generator.round(random.nextDouble(this.min, this.max), this.precision);
                }
            }

            if (op == Operation.Div && this.precision == 0) {
                a -= (int) a % (int) b;
            }

            switch (op) {
                case Operation.Add -> {
                    c = a + b;
                }
                case Operation.Sub -> {
                    c = a - b;
                }
                case Operation.Mul -> {
                    c = a * b;
                }
                case Operation.Div -> {
                    c = a / b;
                }
            }

            double[] arr = new double[3];
            arr[0] = a;
            arr[1] = b;
            arr[2] = c;

            return new AbstractMap.SimpleEntry<double[], Operation>(arr, op);
        }


        abstract public Task generate();

        static public double round(double a, int n) {
            if (n > 9) {
                throw new InvalidParameterException("precision is too high");
            }

            if (n < 0) {
                throw new InvalidParameterException("precision is below zero");
            }

            a *= Math.pow(10, n);
            a = Math.round(a);
            a /= Math.pow(10, n);
            return a;
        }
    }

    protected AbstractMathTask(String text, double res, int prec) {
        this.result = res;
        this.precision = prec;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public Result validate(String answer) {
        double ans = 0;

        try {
            ans = Double.parseDouble(answer);
        } catch (Exception e) {
            return Result.INCORRECT_INPUT;
        }

        if (Math.abs(ExpressionTask.Generator.round(Double.parseDouble(answer), this.precision) - this.result) < Math.pow(0.1, this.precision)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
