package by.mnik0_0.quizer.tasks.math_tasks;

import by.mnik0_0.quizer.Result;

public class AbstractMathTask implements MathTask {
    String text;
    double answer;

    public AbstractMathTask(
            String text,
            double answer
    ) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        System.out.println(answer);
        return text;
    }

    @Override
    public Result validate(String answer) {

        if (this.answer - Double.parseDouble(answer) < 0.1) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}
