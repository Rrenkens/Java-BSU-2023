package by.Kra567.quizer.basics;

/**
 * Class, который описывает один тест
 */
class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     * @param taskLeftCount количество оставшихся заданий
     * @param wrongAnswersCount количество неправильных ответов
     * @param incorrentAnswersCount количество неправильно введенных ответов
     * @param currentTask текущее задание
     */
    private TaskGenerator generator;
    private int taskCount;
    private int taskLeftCount;
    private int wrongAnswersCount;
    private int incorrectAnswersCount;
    private Task currentTask;
    Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
        this.taskLeftCount = taskCount;
        this.wrongAnswersCount = 0;
        this.incorrectAnswersCount = 0;
        this.currentTask = generator.generate();
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result res = currentTask.validate(answer);
        if (res == Result.INCORRECT_INPUT){
            return res;
        }
        if (res == Result.WRONG){
            wrongAnswersCount++;
        }
        taskLeftCount--;
        currentTask = generator.generate();
        return res;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return taskLeftCount == 0;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return (taskCount - taskLeftCount) - wrongAnswersCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongAnswersCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectAnswersCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        return (double)getCorrectAnswerNumber()/(double)taskCount;
    }
}