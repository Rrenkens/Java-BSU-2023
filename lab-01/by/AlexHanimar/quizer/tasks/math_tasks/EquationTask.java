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
                return new EquationTask(String.format("x%s%f = %f (ответ округлите до %d знака после запятой вниз)", op, y, z, precision), x);
            } else {
                return new EquationTask(String.format("%f%sx = %f (ответ округлите до %d знака после запятой вниз)", x, op, z, precision), y);
            }
        }

        @Override
        public EquationTask generate() {
            var rand = new Random();
            while (true) { // if generator is already created, then it can always generate a task
                try {
                    double x = Round(rand.nextDouble(minNumber, maxNumber), precision);
                    double y = Round(rand.nextDouble(minNumber, maxNumber), precision);
                    var op = ops.stream().skip(rand.nextInt(ops.size())).findFirst();
                    if (op.isEmpty())
                        continue;
                    switch (op.get()) {
                        case SUM -> {
                            double t = Round(x + y, precision + 1);
                            return GenerateEquation(x, y, t, " + ");
                        }
                        case DIFF -> {
                            double t = Round(x - y, precision + 1);
                            return GenerateEquation(x, y, t, " - ");
                        }
                        case MUL -> {
                            double t = Round(x * y, Math.max(1, precision * 2));
                            if (x == 0 || y == 0 || t == 0)
                                throw new Exception();
                            return GenerateEquation(x, y, t, " * ");
                        }
                        case DIV -> {
                            if (x == 0 || y == 0)
                                throw new Exception();
                            double t = Round(x / y, Math.max(1, precision * 2));
                            return GenerateEquation(x, y, t, " / ");
                        }
                    }
                } catch (Exception ignored) {
                }
            }
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
