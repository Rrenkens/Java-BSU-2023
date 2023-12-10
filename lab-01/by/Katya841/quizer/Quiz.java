package by.Katya841.quizer;

import by.Katya841.quizer.exceptions.QuizIsFinishedException;
import by.Katya841.quizer.exceptions.QuizNotFinishedException;
import by.Katya841.quizer.tasks.Task;


public class Quiz {
    public int taskCount; // количество заданий
    private Task.Generator taskGenerator;
    private int cntOk;
    private int cntWrong;
    private int cntIncorrect;
    private Task curTask;
    private boolean lastIncorrect;
    Quiz(Task.Generator generator, int taskCount) {
        this.taskCount = taskCount;
        this.taskGenerator = generator;
        lastIncorrect = false;

    }
    Task nextTask() throws QuizNotFinishedException {
        if (cntOk + cntWrong < taskCount) {
            if (lastIncorrect) {
                return curTask;
            }
            curTask = taskGenerator.generate();
            return curTask;
        } else {
            throw new QuizIsFinishedException("QuizIsFinishedException : " + "Attempt to get next task");
        }
    }


    Result provideAnswer(String answer) {
        Result res = curTask.validate(answer);
        lastIncorrect = false;
        if (res == Result.WRONG) {
            cntWrong++;
        } else if (res == Result.OK) {
            cntOk++;
        } else {
            lastIncorrect = true;
            cntIncorrect++;
        }
        return res;
    }

    boolean isFinished() {
        if (cntOk + cntWrong == taskCount) {
            return true;
        }
        return false;
    }

    int getCorrectAnswerNumber() {
        return cntOk;
    }

    int getWrongAnswerNumber() {
        return cntWrong;
    }

    int getIncorrectInputNumber() {
        return cntIncorrect;
    }

    double getMark() {
        double mark = cntOk / (double)taskCount;
        mark *= 10;
        return mark;
    }

}

