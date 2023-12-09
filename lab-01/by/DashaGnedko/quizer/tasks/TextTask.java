package by.DashaGnedko.quizer.tasks;

import by.DashaGnedko.quizer.Result;

public class TextTask implements Task {
    String text;
    String answer;

    public TextTask(
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
        return (answer.equals(this.answer) ? Result.OK : Result.WRONG);
    }
}
