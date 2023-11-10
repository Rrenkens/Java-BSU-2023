package by.Roman191976.Quizer;

public class ExpressionTask implements Task {
    private String taskText;
    private int answer;

    ExpressionTask(String taskText, int answer) {
        this.taskText = taskText;
        this.answer = answer;
    }

    @Override
    public String getText() {
        return taskText;
    }

    @Override
    public Result validate(String answer) {
        int userAnswer;
        try {
            userAnswer = Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        if (userAnswer == this.answer) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
