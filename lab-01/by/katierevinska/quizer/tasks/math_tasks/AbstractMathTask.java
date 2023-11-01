package by.katierevinska.quizer.tasks.math_tasks;

import by.katierevinska.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    String text;
    String answer;
    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        try {
            double d = Double.parseDouble(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        if(Double.parseDouble(this.answer) -Double.parseDouble(answer) < 0.1){//TODO ok if 2.22 and 2.25
            return Result.OK;
        }
        return Result.WRONG;
    }
}
