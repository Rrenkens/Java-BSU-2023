package by.rycbaryana.quizer.tasks;

import by.rycbaryana.quizer.Answer;
import by.rycbaryana.quizer.Result;

public class TextTask implements Task {
    String text;
    String answer;

    public TextTask(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public Result validate(Answer answer) {
        String studentAnswer;
        studentAnswer = answer.getText();
        if (studentAnswer.equals(this.answer)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}