package by.Roman191976.Quizer.tasks.math_tasks;

import by.Roman191976.Quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    private String taskText;
    private int answer;
    
    public AbstractMathTask(String text, int answer) {
        this.taskText = text;
        this.answer = answer;
    }

    @Override
    public Result validate(String userAnswer) {
        int parsedAnswer;
        try {
            parsedAnswer = Integer.parseInt(userAnswer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        if (parsedAnswer == answer) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    @Override
    public String getText() {
        return taskText;
    }
}
