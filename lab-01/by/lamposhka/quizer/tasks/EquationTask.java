package by.lamposhka.quizer.tasks;

import by.lamposhka.quizer.Result;
import by.lamposhka.quizer.Task;
import by.lamposhka.quizer.task_generators.EquationTaskGenerator;

public class EquationTask implements Task {
    private final String text;
    private final int answer;

    public EquationTask(String text, int answer) {
        this.text = text;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) { // temporary
        int intAnswer;
        try {
            intAnswer = Integer.parseInt(answer);
        } catch (NumberFormatException nfe) {
            return Result.INCORRECT_INPUT;
        }
        if (this.answer == intAnswer) {
            return Result.OK;
        }
        return Result.WRONG;
    }
}

