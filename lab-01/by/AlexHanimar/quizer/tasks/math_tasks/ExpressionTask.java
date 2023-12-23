package by.AlexHanimar.quizer.tasks.math_tasks;

import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionTask extends AbstractMathTask {

    public static class Generator extends AbstractMathTask.Generator {
        private final Random rand;

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, precision, ops);
            this.rand = new Random();
        }

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> ops) throws IllegalArgumentException {
            super(minNumber, maxNumber, 0, ops);
            this.rand = new Random();
        }

        private ExpressionTask GenerateExpression(double x, double y, double z, String op) {
            return new ExpressionTask(String.format("%f%s%f = ? (ответ округлите до %d знака после запятой вниз)", x, op, y, Math.max(precision * 2, 1)), z);
        }

        @Override
        public ExpressionTask generate() {
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
                            return GenerateExpression(x, y, t, " + ");
                        }
                        case DIFF -> {
                            double t = Round(x - y, precision + 1);
                            return GenerateExpression(x, y, t, " - ");
                        }
                        case MUL -> {
                            double t = Round(x * y, Math.max(1, precision * 2));
                            return GenerateExpression(x, y, t, " * ");
                        }
                        case DIV -> {
                            if (y == 0.0)
                                throw new Exception();
                            double t = Round(x / y, Math.max(1, precision * 2));
                            return GenerateExpression(x, y, t, " / ");
                        }
                    }
                } catch (Exception ignored) {
                }
            }
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
