package by.busskov.quizer;

public class Quiz {
    public Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (currentTaskNumber >= taskCount) {
            throw new IllegalStateException("Out of tasks");
        }
        if (incorrectInput) {
            return currentTask;
        }
        ++currentTaskNumber;
        currentTask = generator.generate();
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        incorrectInput = false;
        if (result.equals(Result.WRONG)) {
            ++wrongAnswerNumber;
        } else if (result.equals(Result.INCORRECT_INPUT)) {
            incorrectInput = true;
            ++incorrectInputAnswerNumber;
        } else {
            ++correctAnswerNumber;
        }
        return currentTask.validate(answer);
    }

    public boolean isFinished() {
        return currentTaskNumber + 1 == taskCount;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    public int getIncorrectInputAnswerNumber() {
        return incorrectInputAnswerNumber;
    }

    public double getMark() {
        if (!isFinished()) {
            throw new IllegalStateException("Test isn't completed");
        }
        return (double) correctAnswerNumber / taskCount;
    }

    private final TaskGenerator generator;
    private final int taskCount;
    private int currentTaskNumber = 0;
    private Task currentTask = null;
    private int wrongAnswerNumber = 0;
    private int incorrectInputAnswerNumber = 0;
    private int correctAnswerNumber = 0;
    private boolean incorrectInput = false;
}
