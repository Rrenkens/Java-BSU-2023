package by.Dzenia.quizer;
import by.Dzenia.quizer.quiz_exceptions.QuizAnswerAlreadyBeenProvidedException;
import by.Dzenia.quizer.quiz_exceptions.QuizNotFinishedException;
import by.Dzenia.quizer.task_generators.TaskGenerator;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private final TaskGenerator generator;
    private final int taskCount;
    private int countCorrectAnswers = 0;
    private int countWrongAnswers = 0;
    private int countIncorrectAnswers = 0;
    private Task currentTask;
    private boolean incorrectAnswer = false;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    public Task nextTask() throws CannotGenerateTaskException {
        if (!incorrectAnswer) {
            currentTask = generator.generate();
            return currentTask;
        }
        return currentTask;
    }
    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    public Result provideAnswer(String answer) throws QuizAnswerAlreadyBeenProvidedException {
        if (currentTask == null) {
            throw new QuizAnswerAlreadyBeenProvidedException();
        }
        Result result = currentTask.validate(answer);
        switch (result) {
            case INCORRECT_INPUT -> {
                countIncorrectAnswers++;
                incorrectAnswer = true;
            }
            case OK -> {
                countCorrectAnswers++;
                incorrectAnswer = false;
                currentTask = null;
            }
            case WRONG -> {
                countWrongAnswers++;
                incorrectAnswer = false;
                currentTask = null;
            }
        }
        return result;
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return countCorrectAnswers + countWrongAnswers == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    public int getCorrectAnswerNumber() {
        return countCorrectAnswers;
    }

    /**
     * @return количество неправильных ответов
     */
    public int getWrongAnswerNumber() {
        return countWrongAnswers;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    public int getIncorrectInputNumber() {
        return countIncorrectAnswers;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    public double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException("There are unfinished tasks!");
        }
        return (double) countCorrectAnswers / taskCount;
    }
}
