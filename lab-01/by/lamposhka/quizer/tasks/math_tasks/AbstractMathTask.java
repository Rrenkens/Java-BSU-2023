package by.lamposhka.quizer.tasks.math_tasks;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {
    private final String text;
    private final int answer;

    protected AbstractMathTask(
            String text,
            int answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        int intAnswer;
        try {
            intAnswer = Integer.parseInt(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        if (this.answer == intAnswer) {
            return Result.OK;
        }
        return Result.WRONG;
    }

    public static abstract class Generator implements MathTask.Generator {
        private final ArrayList<Operation> operators = new ArrayList<>(4);
        private final int minNumber;
        private final int maxNumber;
        private final Random random = new Random();

        protected Generator(int minNumber,
                            int maxNumber,
                            EnumSet<Operation> validOperations) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            if (validOperations.contains(MathTask.Operation.SUM)) {
                operators.add(MathTask.Operation.SUM);
            }
            if (validOperations.contains(MathTask.Operation.DIFFERENCE)) {
                operators.add(MathTask.Operation.DIFFERENCE);
            }
            if (validOperations.contains(MathTask.Operation.MULTIPLICATION)) {
                operators.add(MathTask.Operation.MULTIPLICATION);
            }
            if (validOperations.contains(MathTask.Operation.DIVISION)) {
                operators.add(MathTask.Operation.DIVISION);
            }
        }

        protected int generateNum() {
            return random.nextInt(maxNumber) + minNumber;
        }

        protected MathTask.Operation generateOperator() {
            return operators.get(random.nextInt(operators.size()));
        }

        protected boolean generateVariablePositionIndicator() {
            return random.nextBoolean();
        }

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }

        @Override
        public abstract AbstractMathTask generate();

    }

}
