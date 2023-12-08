package by.mnik0_0.quizer;

import by.mnik0_0.quizer.Result;
import by.mnik0_0.quizer.Task;
import by.mnik0_0.quizer.exceptions.QuizNotFinishedException;

class Quiz {

    private Task.Generator generator;
    private int taskCount;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private int taskNumber = 0;
    private Task currentTask;
    private boolean incorrectInput = false;

    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    Task nextTask() {
        if (incorrectInput) {
            this.incorrectInput = false;
            return this.currentTask;
        }
        if (isFinished()) {
            return null;
        } else {
            this.currentTask = this.generator.generate();
            this.taskNumber++;
            return currentTask;
        }
    }

    Result provideAnswer(String answer) {
        Result res = this.currentTask.validate(answer);
        if (res == Result.OK) {
            this.correctAnswerNumber++;
            return res;
        }

        if (res == Result.INCORRECT_INPUT) {
            this.incorrectInput = true;
            this.incorrectInputNumber++;
            return res;
        }

        if (res == Result.WRONG) {
            this.wrongAnswerNumber++;
            return res;
        }
        return res;
    }

    boolean isFinished() {
        return this.taskNumber >= this.taskCount;
    }

    int getCorrectAnswerNumber() {
        return this.correctAnswerNumber;
    }

    int getWrongAnswerNumber() {
        return this.wrongAnswerNumber;
    }

    int getIncorrectInputNumber() {
        return this.incorrectInputNumber;
    }

    double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException();
        }
        return (this.correctAnswerNumber / (double) this.taskCount) * 10;
    }
}