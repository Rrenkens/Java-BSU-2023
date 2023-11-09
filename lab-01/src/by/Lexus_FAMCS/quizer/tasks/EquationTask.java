package by.Lexus_FAMCS.quizer.tasks;

import by.Lexus_FAMCS.quizer.Result;

public class EquationTask implements Task {
    private String text;
    private String result;
    public EquationTask(String text, String result) {
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