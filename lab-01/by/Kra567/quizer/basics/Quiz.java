package by.Kra567.quizer.basics;

/**
 * Class, который описывает один тест
 */
public class Quiz {
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
    public Quiz(TaskGenerator generator, int taskCount) throws Exception {
        this.generator = generator;
        this.taskCount = taskCount;
        this.taskLeftCount = taskCount;
        this.wrongAnswersCount = 0;
        this.incorrectAnswersCount = 0;
        try{
            this.currentTask = generator.generate();
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() {
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) throws Exception {
        Result res = currentTask.validate(answer);
        if (res == Result.INCORRECT_INPUT){
            incorrectAnswersCount++;
            return res;
        }
        taskLeftCount--;
        try {
            currentTask = generator.generate();
        } catch (Exception e) {
            throw e;
        }

        if (res == Result.WRONG){
            wrongAnswersCount++;
            return res;
        }

        return res;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return taskLeftCount == 0;
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return (taskCount - taskLeftCount) - wrongAnswersCount;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return wrongAnswersCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return incorrectAnswersCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() {
        return (double)getCorrectAnswerNumber()/(double)taskCount;
    }
    public int taskNumber(){
        return taskCount - taskLeftCount + 1;
    }
}