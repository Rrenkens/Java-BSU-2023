package by.ullliaa.quizer.by.ullliaa.quizer;

import by.ullliaa.quizer.by.ullliaa.quizer.exceptions.QuizIsNotFinished;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private int rAnswers = 0;
    private int wAnswers = 0;
    private int incorrectInput = 0;
    private boolean needTask = true;
    int taskCount_;
    private final TaskGenerator generator_;
    private Task task_;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        generator_ = generator;
        taskCount_ = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() throws Exception {
        if (needTask && rAnswers + wAnswers < taskCount_) {
            task_ = generator_.generate();
        }
        return task_;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        var tmp = task_.validate(answer);
        if (tmp == Result.OK) {
            needTask = true;
            ++rAnswers;
        } else if (tmp == Result.WRONG) {
            needTask = true;
            ++wAnswers;
        } else {
            needTask = false;
            ++incorrectInput;
        }
        return tmp;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return wAnswers + rAnswers == taskCount_;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return rAnswers;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wAnswers;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInput;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        if (!isFinished()) {
            throw new QuizIsNotFinished();
        }
        return ((double) rAnswers) / taskCount_ * 10;
    }
}
