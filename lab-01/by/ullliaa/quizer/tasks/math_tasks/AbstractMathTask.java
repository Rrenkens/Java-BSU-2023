package by.ullliaa.quizer.by.ullliaa.quizer.tasks.math_tasks;

import by.ullliaa.quizer.by.ullliaa.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected double answer;

    @Override
    public Result validate(String answer){
        try {
            double num = Double.parseDouble(answer);
            if (Math.abs(num - this.answer) < 0.000001){
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e){
            return Result.INCORRECT_INPUT;
        }
    }
}
