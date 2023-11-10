package by.Lexus_FAMCS.quizer;

import by.Lexus_FAMCS.quizer.exceptions.QuizNotFinishedException;
import by.Lexus_FAMCS.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private Task.Generator gen;
    private int taskCount;
    private int taskNumber = 0;
    private Task currTask;
    private int correctAnswers;
    private int incorrectInputAnswers;
    private boolean skip = false;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        if (taskCount <= 0) throw new IllegalArgumentException("Number of tasks(taskCount) can't be negative");
        gen = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (skip) {
            skip = false;
            return currTask;
        }
        if (isFinished()) {
            return null;
        } else {
            ++taskNumber;
            return currTask = gen.generate();
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result processAnswer(String answer) {
        Result res = currTask.validate(answer);
        if (res == Result.OK) ++correctAnswers;
        else if (res == Result.INCORRECT_INPUT) {
            ++incorrectInputAnswers;
            skip = true;
        }
        return res;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return taskNumber == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnswers;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return taskCount - correctAnswers;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputAnswers;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    int getMark() {
        if (!isFinished()) throw new QuizNotFinishedException("Quiz haven't finished yet");
        return (int) Math.round((double) correctAnswers / taskCount * 10);
    }
}
