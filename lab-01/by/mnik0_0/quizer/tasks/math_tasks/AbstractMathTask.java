package by.mnik0_0.quizer.tasks.math_tasks;

import by.mnik0_0.quizer.Result;

import java.util.EnumSet;

public class AbstractMathTask implements MathTask {
    public abstract static class Generator implements MathTask.Generator {

        protected int minNumber;
        protected int maxNumber;
        protected EnumSet<Operation> operations;

        Generator(
                int minNumber,
                int maxNumber,
                EnumSet<MathTask.Operation> operations
        ) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;

        }

        @Override
        public abstract MathTask generate();

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }
    }

    String text;
    double answer;

    public AbstractMathTask(
            String text,
            double answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        System.out.println(answer);
        return text;
    }

    @Override
    public Result validate(String answer) {

        if (this.answer - Double.parseDouble(answer) < 0.1) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
