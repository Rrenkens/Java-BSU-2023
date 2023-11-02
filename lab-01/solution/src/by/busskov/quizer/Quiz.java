package by.busskov.quizer;

public class Quiz {
    private TaskGenerator generator;
    private int taskCount;
    private int currentTaskNumber = 0;

    private Task currentTask = null;

    private int wrongAnswerNumber = 0;

    private int incorrectInputAnswerNumber = 0;

    private int correctAnswerNumber = 0;

    private boolean incorrectInput = false;

    Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    Task nextTask() {
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

    Result provideAnswer(String answer) {
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

    boolean isFinished() {
        return currentTaskNumber + 1 == taskCount;
    }

    int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    int getIncorrectInputAnswerNumber() {
        return incorrectInputAnswerNumber;
    }

    double getMark() {
        if (!isFinished()) {
            throw new IllegalStateException("Test isn't completed");
        }
        return (double) correctAnswerNumber / taskCount;
    }
}
