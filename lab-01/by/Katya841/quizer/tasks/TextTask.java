package by.Katya841.quizer.tasks;


import by.Katya841.quizer.Result;

public class TextTask implements Task {
    public final String text;
    private final String answer;
    public TextTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (this.answer.equals(answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

}