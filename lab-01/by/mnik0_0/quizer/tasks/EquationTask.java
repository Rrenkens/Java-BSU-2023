package by.mnik0_0.quizer.tasks;

import by.mnik0_0.quizer.Result;
import by.mnik0_0.quizer.Task;

public class EquationTask implements Task {
    String text;
    double answer;

    public EquationTask(
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
