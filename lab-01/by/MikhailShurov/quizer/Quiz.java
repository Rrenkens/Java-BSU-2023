package by.MikhailShurov.quizer;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    private int taskCount;
    Task.Generator generator;

    Task lastTask;
    int lastTaskIndex = 0;
    boolean lastInputWasIncorrect = false;

    int incorrectAnswersCount = 0;
    int incorrectInputCount = 0;

    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (lastInputWasIncorrect) {
            return lastTask;
        } else if (!isFinished()) {
            this.lastTask = generator.generate();
            ++ lastTaskIndex;
            return this.lastTask;
        } else  {
            return null;
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */

    Result provideAnswer(String answer) {
        if (lastTask.validate(answer) == Result.OK) {
            lastInputWasIncorrect = false;
            return Result.OK;
        } else if (lastTask.validate(answer) == Result.WRONG) {
            ++ incorrectAnswersCount;
            lastInputWasIncorrect = false;
            return Result.WRONG;
        } else {
            ++ incorrectInputCount;
            lastInputWasIncorrect = true;
            return Result.INCORRECT_INPUT;
        }
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return lastTaskIndex == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return taskCount - incorrectAnswersCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return incorrectAnswersCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputCount;
    }

    int getTotalAnswers() {
        return taskCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        if (isFinished()) {
            return Double.valueOf(getCorrectAnswerNumber()) / taskCount * 10;
        } else {
            throw new IllegalStateException("Test is not finished");
        }
    }
}