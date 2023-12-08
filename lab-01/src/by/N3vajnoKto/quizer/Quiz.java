package by.N3vajnoKto.quizer;

import by.N3vajnoKto.quizer.exception.NoTasksLeftException;
import by.N3vajnoKto.quizer.exception.NoValidGeneratorsException;
import by.N3vajnoKto.quizer.exception.QuizNotFinishedException;

import java.util.ArrayList;

public class Quiz {
    private ArrayList<Task> tasks;
    private int cur;
    private int correct;
    private int incorrect;
    private int wrong;

    Quiz(Task.Generator generator, int taskCount) throws Exception {
        this.tasks = new ArrayList<Task>();
        for (int i = 0; i < taskCount; ++i) {
            this.tasks.addLast(generator.generate());
        }
        this.cur = 0;
        this.correct = 0;
        this.incorrect = 0;
        this.wrong = 0;
    }

    Task nextTask() {
        return this.tasks.get(cur);
    }

    Result provideAnswer(String answer) {
        var res = this.tasks.get(cur).validate(answer);

        if (res == Result.OK) {
            ++this.correct;
            ++cur;
        } else if (res == Result.WRONG) {
            ++this.wrong;
            ++cur;
        } else {
            ++this.incorrect;
        }


        return res;
    }

    boolean isFinished() {
        return cur == this.tasks.size();
    }

    int getCorrectAnswerNumber() {
        return this.correct;
    }

    int getWrongAnswerNumber() {
        return this.wrong;
    }

    int getIncorrectInputNumber() {
        return this.incorrect;
    }

    double getMark() throws QuizNotFinishedException {
        if (!this.isFinished()) {
            throw new QuizNotFinishedException("cannot get mark until quize's over");
        }
        return (double) this.correct / (double) (this.tasks.size()) * 100;
    }
}