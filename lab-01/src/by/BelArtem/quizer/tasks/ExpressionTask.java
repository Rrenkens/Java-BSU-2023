package by.BelArtem.quizer.tasks;

import by.BelArtem.quizer.Task;
import by.BelArtem.quizer.Result;

public class ExpressionTask implements Task {
    private final String text;
    private final int answer;

    public ExpressionTask(String text, int answer){
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText(){
        return text;
    }

    @Override
    public Result validate(String answer){
        if (answer == null){
            return Result.INCORRECT_INPUT;
        }
        try {
            Integer.parseInt(answer);
        } catch (Exception e){
            return Result.INCORRECT_INPUT;
        }
        if (this.answer - Integer.parseInt(answer) == 0) {
            return Result.OK;
        }
        return Result.WRONG;
    }

}
