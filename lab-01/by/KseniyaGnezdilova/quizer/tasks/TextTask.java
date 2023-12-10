package by.KseniyaGnezdilova.quizer.tasks;

import by.KseniyaGnezdilova.quizer.Result;

import java.util.Objects;

public class TextTask implements Task {
    private String text;
    private String answer;
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
    public Result validate(String answer) {
        if (Objects.equals(this.answer, answer)){
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}