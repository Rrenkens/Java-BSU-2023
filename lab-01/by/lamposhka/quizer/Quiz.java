package by.lamposhka.quizer;

/**
 * Class, который описывает один тест
 */
class Quiz {

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    Task nextTask() {
        // ...
        return null;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        // ...
        return null;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        // ...
        return true;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        // ...
        return 0;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        // ...
        return 0;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        // ...
        return 0;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        // ...
        return 0;
    }
}
