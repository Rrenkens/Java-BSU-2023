package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;

public class ExpressionTask implements Task {
    String text;
    String result;
    public ExpressionTask(String text, String result) {
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
