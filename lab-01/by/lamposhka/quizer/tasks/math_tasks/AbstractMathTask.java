package by.lamposhka.quizer.tasks.math_tasks;

import by.lamposhka.quizer.tasks.Result;

public abstract class AbstractMathTask implements MathTask {
    private final String text;
    private final int answer;

    //enum to be added
    AbstractMathTask(
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
