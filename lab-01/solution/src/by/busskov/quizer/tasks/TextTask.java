package by.busskov.quizer.tasks;

import by.busskov.quizer.Result;
import by.busskov.quizer.Task;

public class TextTask implements Task {
    public TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String userAnswer) {
        if (answer.equals(userAnswer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    private final String text;
    private final String answer;
}
