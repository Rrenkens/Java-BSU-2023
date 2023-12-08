package by.N3vajnoKto.quizer.tasks.math_tasks;

import by.N3vajnoKto.quizer.Task;

import java.util.EnumMap;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    static public class Generator extends AbstractMathTask.Generator {

        public Generator(double min, double max, int prec, EnumSet<AbstractMathTask.Operation> st) {
            super(min, max, prec, st);
        }

        @Override
        public Task generate() {
            var expr = this.generateExpr();

            double a = expr.getKey()[0];
            double b = expr.getKey()[1];
            double c = expr.getKey()[2];
            AbstractMathTask.Operation op = expr.getValue();

            EnumMap<Operation, String> mp = new EnumMap<Operation, String>(Operation.class);
            mp.put(Operation.Add, "+");
            mp.put(Operation.Sub, "-");
            mp.put(Operation.Mul, "*");
            mp.put(Operation.Div, "/");

            return new ExpressionTask(format(a, this.precision()) + " " + mp.get(op) + " " + format(b, this.precision()) + " = ? (precision: " + this.precision() + ")", c, this.precision());
        }
    }

    public ExpressionTask(String text, double res, int prec) {
        super(text, res, prec);
    }
}
