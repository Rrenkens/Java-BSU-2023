package by.BelArtem.quizer;

import by.BelArtem.quizer.exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */

class Quiz {
    private Task cacheTask;

    private Task currentTask;

    private final TaskGenerator generator;
    private final int taskCount;

    private int currentTaskIndex = -1;
    private int correctAnswersCount = 0;
    private int wrongAnswersCount = 0;
    private int incorrectInputCount = 0;

    public Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    public Task nextTask() throws Exception {
        if (cacheTask != null){
            return cacheTask;
        }

        Task result;
        currentTaskIndex++;
        result = generator.generate();
        currentTask = result;
        return result;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) {
        Task task = this.currentTask;
        Result result = task.validate(answer);
        switch (result){
            case OK -> {
                correctAnswersCount++;
                cacheTask = null;
            }
            case WRONG -> {
                wrongAnswersCount++;
                cacheTask = null;
            }
            case INCORRECT_INPUT -> {
                incorrectInputCount++;
                cacheTask = task;
            }
            default -> {}
        }

        return result;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return (taskCount == currentTaskIndex + 1 && cacheTask == null);
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return correctAnswersCount;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return this.wrongAnswersCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return this.incorrectInputCount;
    }

    public int getTaskCount(){
        return this.taskCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() throws QuizNotFinishedException {
        if (!this.isFinished()){
            throw new QuizNotFinishedException("Quiz is not finished yet");
        }
        return  (double) this.correctAnswersCount / this.taskCount;
    }
}
