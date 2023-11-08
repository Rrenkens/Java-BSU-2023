package by.Bvr_Julia.quizer.tasks;

import by.Bvr_Julia.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected int answer;
    @Override
    public Result validate(String answer){
        try {
            int num = Integer.parseInt(answer);
            if (num == this.answer){
                return Result.OK;
            }else{
                return Result.WRONG;
            }
        } catch (NumberFormatException e){
            return Result.INCORRECT_INPUT;
        }
    }
    public abstract static class Generator implements MathTask.Generator {
    }
}
