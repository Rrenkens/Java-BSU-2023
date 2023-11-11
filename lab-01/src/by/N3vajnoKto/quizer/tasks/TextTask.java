package by.N3vajnoKto.quizer.tasks;

import by.N3vajnoKto.quizer.Result;
import by.N3vajnoKto.quizer.Task;
import by.N3vajnoKto.quizer.tasks.math_tasks.AbstractMathTask;


public class TextTask implements Task {
    private String text;
    private String answer;

    public TextTask(String text, String ans) {
        this.text = text;
        this.answer = ans;
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
