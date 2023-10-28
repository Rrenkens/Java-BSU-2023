package by.lamposhka.quizer.tasks;

import by.lamposhka.quizer.Result;
import by.lamposhka.quizer.Task;

public class ExpressionTask implements Task {
    private String text;
    private int answer;

    //temporary constructor. enum to be added
    public ExpressionTask(
            String text,
            int answer
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
        int intAnswer;
        try {
            intAnswer = Integer.parseInt(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        if (this.answer == intAnswer) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
