package by.DashaGnedko.quizer;

import by.DashaGnedko.quizer.tasks.Task;
import by.DashaGnedko.quizer.exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private Task.Generator generator;
    private int taskCount;
    private Task currentTask;
    private int incorrect = 0;
    private int wrong = 0;
    private int correct = 0;
    private boolean isLastAnsIncorrect = false;

    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (!isLastAnsIncorrect) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    public Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        isLastAnsIncorrect = false;
        if (result == Result.INCORRECT_INPUT) {
            incorrect++;
            isLastAnsIncorrect = true;
        } else if (result == Result.OK) {
            correct++;
        } else {
            wrong++;
        }
        return result;
    }

    public boolean isFinished() {
        return (correct + wrong == taskCount);
    }

    public int getCorrectAnswerNumber() {
        return correct;
    }

    public int getWrongAnswerNumber() {
        return wrong;
    }

    public int getIncorrectInputNumber() {
        return incorrect;
    }

    public double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Quiz is still running");
        }
        return (double) correct / taskCount * 100;
    }
}