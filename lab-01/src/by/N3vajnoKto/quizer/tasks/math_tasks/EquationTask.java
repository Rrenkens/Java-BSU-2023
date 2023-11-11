package by.N3vajnoKto.quizer.tasks.math_tasks;

import by.N3vajnoKto.quizer.Task;

import java.util.EnumMap;

import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {
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

            if (a == 0 && (op == AbstractMathTask.Operation.Mul || op == AbstractMathTask.Operation.Div)) {
                return new EquationTask("x " + mp.get(op) + " " + format(b, this.precision()) + " = " + format(c, this.precision()) + " (precision: " + this.precision() + ")", a, this.precision());
            }

            Random random = new Random();

            if (random.nextBoolean()) {
                return new EquationTask("x " + mp.get(op) + " " + format(b, this.precision()) + " = " + format(c, this.precision()) + " (precision: " + this.precision() + ")", a, this.precision());
            } else {
                return new EquationTask(format(a, this.precision()) + " " + mp.get(op) + " x " + " = " + format(c, this.precision()) + " (precision: " + this.precision() + ")", b, this.precision());
            }
        }
    }

    public EquationTask(String text, double res, int prec) {
        super(text, res, prec);
    }

}
