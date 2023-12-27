package by.Roman191976.Quizer.tasks.real_math_tasks;

import by.Roman191976.Quizer.Result;

public abstract class AbstractRealMathTask implements RealMathTask {
    private String taskText;
    private double answer;
    
    public AbstractRealMathTask(String text, double answer) {
        this.taskText = text;
        this.answer = answer;
    }

    @Override
    public Result validate(String userAnswer) {
        double parsedAnswer;
        try {
            parsedAnswer = Double.parseDouble(userAnswer);
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
