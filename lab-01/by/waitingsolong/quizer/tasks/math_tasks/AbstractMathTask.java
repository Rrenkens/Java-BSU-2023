package by.waitingsolong.quizer.tasks.math_tasks;

import by.waitingsolong.quizer.Result;
import by.waitingsolong.quizer.tasks.TextTask;

import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask extends TextTask implements MathTask {
    protected static boolean isNumeric(String str) {
        return str.matches("-?\\d+(,\\d+)?");
    }

    protected static double randRange(double min, double max) {
        return (new Random()).nextDouble() * (max - min) + min;
    }

    public static abstract class Generator implements MathTask.Generator {
        protected Operation[] ops;
        protected double  minNumber;
        protected double maxNumber;
        protected int precision;
        public double getMinNumber() {
            return this.minNumber;
        }
        public double getMaxNumber() {
            return this.maxNumber;
        }
        protected String fmt() {
            return "%." + this.precision + "f";
        }

        /**
         * @param minNumber             минимальное число
         * @param maxNumber             максимальное число
         * @param op                    оп
         */
        protected Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> op,
                int precision
        ) {
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("max is less than min given to AbstractMathTask");
            }
            if (precision < 0) {
                throw new IllegalArgumentException("given wrong precision");
            }

            int opCount = 0;
            if (op.contains(Operation.Sum)) {
                opCount++;
            }
            if (op.contains(Operation.Difference)) {
                opCount++;
            }
            if (op.contains(Operation.Multiplication)) {
                opCount++;
            }
            if (op.contains(Operation.Division)) {
                opCount++;
            }
            ops = new Operation[opCount];
            int iter = 0;
            if (op.contains(Operation.Sum)) {
                ops[iter++] = Operation.Sum;
            }
            if (op.contains(Operation.Difference)) {
                ops[iter++] = Operation.Difference;
            }
            if (op.contains(Operation.Multiplication)) {
                ops[iter++] = Operation.Multiplication;
            }
            if (op.contains(Operation.Division)) {
                ops[iter++] = Operation.Division;
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.precision = precision;
        }
    }

    protected AbstractMathTask(
            String text,
            String answer
    ) {
        super(text, answer);
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(String answer) {
        if (!isNumeric(this.answer)) {
            return Result.INCORRECT_INPUT;
        } else if (this.answer.equals(answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
