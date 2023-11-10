package by.AlexHanimar.quizer.tasks.math_tasks;

import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, precision, ops);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, 0, ops);
        }

        @Override
        public ExpressionTask generate() throws TaskGenerationException {
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
                                return new ExpressionTask(String.format("%f + %f = ?", x, y), t);
                            throw new Exception();
                        }
                        case DIFF -> {
                            double t = Round(x - y);
                            if (minNumber <= t && t <= maxNumber)
                                return new ExpressionTask(String.format("%f - %f = ?", x, y), t);
                            throw new Exception();
                        }
                        case MUL -> {
                            double t = Round(x * y);
                            if (minNumber <= t && t <= maxNumber)
                                return new ExpressionTask(String.format("%f * %f = ?", x, y), t);
                            throw new Exception();
                        }
                        case DIV -> {
                            if (y == 0.0)
                                throw new Exception();
                            double t = Round(x / y);
                            if (Math.abs(t * y - x) > 1e-9)
                                throw new Exception();
                            if (minNumber <= t && t <= maxNumber)
                                return new ExpressionTask(String.format("%f / %f = ?", x, y), t);
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

    public ExpressionTask(String text, double answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }
}
