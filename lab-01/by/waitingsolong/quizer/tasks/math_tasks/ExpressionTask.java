package by.waitingsolong.quizer.tasks.math_tasks;

import java.util.EnumSet;
import java.util.Random;

public class ExpressionTask extends AbstractMathTask {
    static class Generator extends AbstractMathTask.Generator {
        private Random rand = new Random();

        /**
         * @param minNumber             минимальное число
         * @param maxNumber              максимальное число
         * @param op
         */
        Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> op,
                int precision
        ) {
            super(minNumber, maxNumber, op, precision);
        }
        /**
         * return задание типа {@link ExpressionTask}
         */
        @Override
        public AbstractMathTask generate() {
            if (ops.length == 0) {
                throw new RuntimeException("No operations in ExpressionTaskGenerator provided");
            }

            Operation randomOperation  = ops[rand.nextInt(ops.length)];
            double randomLhs = randRange(this.minNumber, this.maxNumber);
            double randomRhs = randRange(this.minNumber, this.maxNumber);
            if (randomOperation == Operation.Sum) {
                return new ExpressionTask(String.format(fmt() + " + " + fmt() + " = ?", randomLhs, randomRhs), Double.toString(randomLhs + randomRhs));
            }
            if (randomOperation == Operation.Difference) {
                return new ExpressionTask(String.format(fmt() + " - " + fmt() + " = ?", randomLhs, randomRhs), Double.toString(randomLhs - randomRhs));
            }
            if (randomOperation == Operation.Multiplication) {
                return new ExpressionTask(String.format(fmt() + " * " + fmt() + " = ?", randomLhs, randomRhs), Double.toString(randomLhs * randomRhs));
            }
            if (randomOperation == Operation.Division) {
                return new ExpressionTask(String.format(fmt() + " / " + fmt() + " = ?", randomLhs, randomRhs), Double.toString(randomLhs / randomRhs));
            }

            throw new RuntimeException("ExpressionTask generate() out of bounds");
        }
    }
    String text;
    String answer;

    public ExpressionTask(
            String text,
            String answer
    ) {
        super(text, answer);
    }
}
