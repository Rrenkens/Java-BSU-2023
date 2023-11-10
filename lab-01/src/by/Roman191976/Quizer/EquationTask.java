package by.Roman191976.Quizer;

public class EquationTask implements Task {
    private String taskText;
    private int answer;

    EquationTask(String taskText, int answer) {
        this.taskText = taskText;
        this.answer = answer;
    }

    @Override
    public Result validate(String userAnswer) {
        int parsedAnswer;
        try {
            parsedAnswer = Integer.parseInt(userAnswer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        if (parsedAnswer == answer) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    @Override
    public String getText() {
        return taskText;
    }
}
