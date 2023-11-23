package by.mnik0_0.quizer;

import by.mnik0_0.quizer.Result;
import by.mnik0_0.quizer.Task;

/**
 * Class, который описывает один тест
 */
class Quiz {

    private Task.Generator generator;
    private int taskCount;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private int taskNumber = 0;
    private Task currentTask;
    private boolean incorrectInput = false;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
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

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
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

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return this.taskNumber >= this.taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return this.correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return this.wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return this.incorrectInputNumber;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() {
        return (this.correctAnswerNumber / (double) this.taskCount) * 10;
    }
}