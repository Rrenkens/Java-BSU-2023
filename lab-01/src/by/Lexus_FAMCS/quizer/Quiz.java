package by.Lexus_FAMCS.quizer;

import by.Lexus_FAMCS.quizer.task_generators.TaskGenerator;
import by.Lexus_FAMCS.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private TaskGenerator gen;
    private int taskCount;
    private int taskNumber = -1;
    private Task currTask;
    private int correctAnswers;
    private int incorrectInputAnswers;
    private boolean skip = false;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        gen = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (isFinished()) {
            return null;
        } else {
            if (skip) {
                skip = false;
                return currTask;
            } else {
                ++taskNumber;
                return gen.generate();
            }
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result res = currTask.validate(answer);
        switch (res) {
            case Result.OK -> ++correctAnswers;
            case Result.INCORRECT_INPUT -> { ++incorrectInputAnswers; skip = true; }
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
    double getMark() {
        return (double) correctAnswers / taskCount;
    }
}
