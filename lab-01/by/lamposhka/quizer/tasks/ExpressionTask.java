package by.lamposhka.quizer.tasks;

public class ExpressionTask implements Task {
    private final String text;
    private final int answer; // is it supposed to be double, huh?

    //enum to be added
    public ExpressionTask(
            String text,
            int answer
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
