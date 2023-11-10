package by.katierevinska.quizer.tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

import java.util.Objects;

public class TextTask implements Task {

    private final String text;
    private final String answer;

    TextTask(
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
        if (Objects.equals(answer, this.answer)) {
            return Result.OK;
        }
        return Result.WRONG;
    }

    // ...
}
