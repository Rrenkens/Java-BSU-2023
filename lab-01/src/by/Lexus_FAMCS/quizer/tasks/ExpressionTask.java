package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;

public class ExpressionTask implements Task {
    String text;
    public ExpressionTask(String text) {
        this.text = text;
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        return text.equals(answer) ? Result.OK : Result.WRONG;
    }
}
