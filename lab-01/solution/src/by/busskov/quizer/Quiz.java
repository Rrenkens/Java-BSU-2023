package by.busskov.quizer;

import by.busskov.quizer.exceptions.GenerateException;
import by.busskov.quizer.exceptions.QuizNotFinishedException;
import by.busskov.quizer.exceptions.QuizOutOfTasksException;

public class Quiz {
    public Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (currentTaskNumber >= taskCount) {
            throw new QuizOutOfTasksException("There is no available tasks");
        }
        if (incorrectInput) {
            return currentTask;
        }
        boolean exceptionThrown = true;
        while (exceptionThrown) {
            try {
                currentTask = generator.generate();
                exceptionThrown = false;
            } catch (GenerateException ignored) {
            }
        }
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        incorrectInput = false;
        if (result.equals(Result.WRONG)) {
            ++wrongAnswerNumber;
            ++currentTaskNumber;
        } else if (result.equals(Result.INCORRECT_INPUT)) {
            incorrectInput = true;
            ++incorrectInputAnswerNumber;
        } else {
            ++correctAnswerNumber;
            ++currentTaskNumber;
        }
        return result;
    }

    public boolean isFinished() {
        return currentTaskNumber + 1 > taskCount;
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
            throw new QuizNotFinishedException("Test isn't completed");
        }
        return (double) correctAnswerNumber / taskCount;
    }

    private final Task.Generator generator;
    private final int taskCount;
    private int currentTaskNumber = 0;
    private Task currentTask = null;
    private int wrongAnswerNumber = 0;
    private int incorrectInputAnswerNumber = 0;
    private int correctAnswerNumber = 0;
    private boolean incorrectInput = false;
}
