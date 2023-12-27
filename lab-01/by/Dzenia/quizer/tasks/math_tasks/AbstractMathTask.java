package by.Dzenia.quizer.tasks.math_tasks;
import by.Dzenia.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected double minNumber;
    protected double maxNumber;
    protected String taskText;
    protected double answer;
    protected double precision;
    protected boolean answerIsPossibleBeZero = true;
    public String getText() {
        return taskText;
    }

    public Result validate(String answer) {
        double answerDouble;
        try {
            answerDouble = Double.parseDouble(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
        if (answerDouble == 0 && !answerIsPossibleBeZero) {
            return Result.WRONG;
        }
        if (Double.isNaN(this.answer)) {
            return Result.OK;
        }
        if (Math.abs(answerDouble - this.answer) <= Math.pow(10, -precision)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
