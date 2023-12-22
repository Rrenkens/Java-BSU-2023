package by.katierevinska.quizer;

import by.katierevinska.quizer.exceptions.QuizFinishedException;
import by.katierevinska.quizer.exceptions.QuizNotFinishedException;

import java.util.ArrayList;

public class Quiz {
    private final int taskCount;
    private int currentIndex;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private Task.Generator generator;
    private ArrayList<Task> test = new ArrayList<>();

    Quiz(Task.Generator generator, int taskCount) {
        if (taskCount == 0) {
            throw new IllegalArgumentException("number of tasks should be more than 0");
        }
        this.taskCount = taskCount;
        this.currentIndex = 0;
        this.generator = generator;
    }

    Task nextTask() {
        if (isFinished()) {
            throw new QuizFinishedException("Test already finished , you can't get nextTask");
        }
        test.add(generator.generate());
        return test.get(currentIndex);
    }

    Result provideAnswer(String answer) {
        Result tmp = this.test.get(currentIndex).validate(answer);
        if (tmp == Result.OK) {
            correctAnswerNumber++;
            currentIndex++;
        } else if (tmp == Result.WRONG) {
            this.wrongAnswerNumber++;
            currentIndex++;
        } else {
            this.incorrectInputNumber++;
        }
        return tmp;
    }

    boolean isFinished() {
        return currentIndex > taskCount - 1;
    }

    int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Test doesn't finished yet, you can't get mark");
        }
        return ((double) correctAnswerNumber) / taskCount;
    }
}
