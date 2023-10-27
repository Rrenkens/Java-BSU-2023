package by.waitingsolong.quizer.tasks.math_tasks;

import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        private Random rand = new Random();

        /**
         * @param minNumber             минимальное число
         * @param maxNumber              максимальное число
         * @param op
         */
        public Generator(
                double minNumber,
                double maxNumber,
                EnumSet<Operation> op,
                int precision
        ) {
            super(minNumber, maxNumber, op, precision);
        }

        /**
         * return задание типа {@link EquationTask}
         */
        @Override
        public AbstractMathTask generate() {
            if (ops.length == 0) {
                throw new RuntimeException("No operations in EquationTaskGenerator provided");
            }

            Operation randomOperation  = ops[rand.nextInt(ops.length)];
            double randomNum = rand.nextDouble(this.maxNumber - this.minNumber + 1) + this.minNumber;
            double randomAns = rand.nextDouble(this.maxNumber - this.minNumber + 1) + this.minNumber;
            int xPos = rand.nextInt(2);
            if (xPos == 0) { // x<operator><num2>=<answer>
                if (randomOperation == Operation.Sum) {
                    return new EquationTask(String.format("x + " + fmt() + " = " + fmt(), randomNum, randomAns), String.format(fmt(),randomAns - randomNum));
                }
                if (randomOperation == Operation.Difference) {
                    return new EquationTask(String.format("x - " + fmt() + " = " + fmt(), randomNum, randomAns), String.format(fmt(),randomAns + randomNum));
                }
                if (randomOperation == Operation.Multiplication) {
                    if (randomNum == 0) {
                        do {
                            randomNum = randRange(this.minNumber, this.maxNumber);;
                        } while (randomNum == 0);
                    }
                    return new EquationTask(String.format("x * " + fmt() + " = " + fmt(), randomNum, randomAns), String.format(fmt(),randomAns / randomNum));
                }
                if (randomOperation == Operation.Division) {
                    if (randomNum == 0) {
                        do {
                            randomNum = randRange(this.minNumber, this.maxNumber);;
                        } while (randomNum == 0);
                    }
                    return new EquationTask(String.format("x / " + fmt() + " = " + fmt(), randomNum, randomAns), String.format(fmt(),randomNum * randomAns));
                }
            } else {
                if (randomOperation == Operation.Sum) {
                    return new EquationTask(String.format(fmt() + " + x = " + fmt(), randomNum, randomAns), String.format(fmt(), randomAns - randomNum));
                }
                if (randomOperation == Operation.Difference) {
                    return new EquationTask(String.format(fmt() + " - x = " + fmt(), randomNum, randomAns), String.format(fmt(),randomNum - randomAns));
                }
                if (randomOperation == Operation.Multiplication) {
                    if (randomNum == 0) {
                        do {
                            randomNum = randRange(this.minNumber, this.maxNumber);
                        } while (randomNum == 0);
                    }
                    return new EquationTask(String.format(fmt() + " * x = " + fmt(), randomNum, randomAns), String.format(fmt(),randomAns / randomNum));
                }
                if (randomOperation == Operation.Division) {
                    if (randomAns == 0) {
                        do {
                            randomAns = randRange(this.minNumber, this.maxNumber);
                        } while (randomAns == 0);
                    }
                    return new EquationTask(String.format(fmt() + " / x = " + fmt(), randomNum, randomAns), String.format(fmt(),randomNum / randomAns));
                }
            }

            throw new RuntimeException("EquationTask generate() out of bounds");
        }
    }
    public EquationTask(
            String text,
            String answer
    ) {
        super(text, answer);
    }
}
