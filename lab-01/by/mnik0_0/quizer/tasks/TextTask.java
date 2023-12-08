package by.mnik0_0.quizer.tasks;

import by.mnik0_0.quizer.Result;
import by.mnik0_0.quizer.Task;

import java.util.Objects;


public class TextTask implements Task {

    private String text;
    private String answer;

    TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(String answer) {
        if (Objects.equals(answer, this.answer)) {
            return Result.OK;
        }

        return Result.WRONG;
    }
}