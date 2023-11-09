package by.Lexus_FAMCS.quizer.tasks.math_tasks;

import by.Lexus_FAMCS.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    private String text;
    private String result;
    AbstractMathTask(String text, String result) {
        this.text = text;
        this.result = result;
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return result.equals(answer) ? Result.OK : Result.WRONG;
    }
}
