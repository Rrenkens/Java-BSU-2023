package by.nrydo.quizer.tasks.math_tasks;

import by.nrydo.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    public enum Operation {
        SUM,
        Difference,
        Multiplication,
        Division
    }

    protected String text;
    protected String answer;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {

        try {
            Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        return answer.equals(this.answer) ? Result.OK : Result.WRONG;
    }
}
