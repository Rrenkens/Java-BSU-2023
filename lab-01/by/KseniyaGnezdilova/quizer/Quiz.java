package by.KseniyaGnezdilova.quizer;

import by.KseniyaGnezdilova.quizer.tasks.Task;

class Quiz {

    private Task currentTask;
    private int taskCount;

    private Task.Generator generator;
    private boolean status;
    private int  correctAnswerNumber;
    private int wrongAnswerNumber;

    private int incorrectInputNumber;

    Quiz(Task.Generator generator, int taskCount) {
        this.currentTask = null;
        this.taskCount = taskCount;
        this.generator = generator;
        this.status = true;
        this.correctAnswerNumber = 0;
        this.wrongAnswerNumber =  0;
        this.incorrectInputNumber = 0;
    }

    Task repeatTask(){
        return currentTask;
    }
    Task nextTask() {
        currentTask = generator.generate();
        return currentTask;
    }

    Result provideAnswer(String answer) {
        Result result = currentTask.validate(answer);
        switch (result){
            case OK -> this.correctAnswerNumber++;
            case WRONG -> this.wrongAnswerNumber++;
            case INCORRECT_INPUT -> {
                this.incorrectInputNumber++;
                repeatTask();
            }
        }
        return result;
    }

    boolean isFinished() {
        this.status = (this.correctAnswerNumber + this.wrongAnswerNumber == taskCount);
        return this.status;
    }

    int getTaskCount(){ return taskCount; }
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
        return (double)getCorrectAnswerNumber() / (double)getTaskCount() * 100;
    }
}
