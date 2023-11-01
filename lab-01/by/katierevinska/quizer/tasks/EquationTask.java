package by.katierevinska.quizer.tasks;

import by.katierevinska.quizer.Result;
import by.katierevinska.quizer.Task;

import java.util.Objects;

public class EquationTask implements Task {
    String text;
    String answer;
    public EquationTask(
            String text,
            String answer
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