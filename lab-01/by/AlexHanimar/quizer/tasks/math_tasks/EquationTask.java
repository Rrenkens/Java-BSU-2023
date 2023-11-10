package by.AlexHanimar.quizer.tasks.math_tasks;

import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, precision, ops);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, 0, ops);
        }

        private EquationTask GenerateEquation(double x, double y, double z, String op) {
            var rand = new Random();
            if (rand.nextBoolean()) {
                return new EquationTask(String.format("x%s%f = %f", op, y, z), x);
            } else {
                return new EquationTask(String.format("%f%sx = %f", x, op, z), y);
            }
        }

        @Override
        public EquationTask generate() throws TaskGenerationException {
            var rand = new Random();
            for (int i = 0;i < 100;i++) {
                try {
                    double x = Round(rand.nextDouble(minNumber, maxNumber));
                    double y = Round(rand.nextDouble(minNumber, maxNumber));
                    var op = ops.stream().skip(rand.nextInt(ops.size())).findFirst();
                    if (op.isEmpty())
                        continue;
                    switch (op.get()) {
                        case SUM -> {
                            double t = Round(x + y);
                            if (minNumber <= t && t <= maxNumber)
                                return GenerateEquation(x, y, t, " + ");
                            throw new Exception();
                        }
                        case DIFF -> {
                            double t = Round(x - y);
                            if (minNumber <= t && t <= maxNumber)
                                return GenerateEquation(x, y, t, " - ");
                            throw new Exception();
                        }
                        case MUL -> {
                            double t = Round(x * y);
                            if (x == 0 || y == 0 || t == 0)
                                throw new Exception();
                            if (minNumber <= t && t <= maxNumber)
                                return GenerateEquation(x, y, t, " * ");
                            throw new Exception();
                        }
                        case DIV -> {
                            if (x == 0 || y == 0 || x / y == 0)
                                throw new Exception();
                            double t = Round(x / y);
                            if (Math.abs(t * y - x) > 1e-9)
                                throw new Exception();
                            if (minNumber <= t && t <= maxNumber)
                                return GenerateEquation(x, y, t, " / ");
                            throw new Exception();
                        }
                    };
                } catch (Exception ex) {
                    continue;
                }
            }
            throw new TaskGenerationException();
        }
    }

    public EquationTask(String text, double answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
