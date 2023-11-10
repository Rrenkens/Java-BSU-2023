package by.Lenson423.quizer;

import by.Lenson423.quizer.exceptions.CantGenerateTask;
import by.Lenson423.quizer.exceptions.NoTaskWasGeneratedException;
import by.Lenson423.quizer.exceptions.QuizIsFinishedException;
import by.Lenson423.quizer.exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private final Task.Generator generator;
    private final int taskCount;
    private int currentTaskNumber = 0;
    private int correctAnswerNumber = 0;

    private int incorrectInputNumber = 0;
    private Task currentTask;

    private boolean needNew = true;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        if (generator == null) {
            throw new IllegalArgumentException("Generator is null");
        }
        this.generator = generator;
        if (taskCount <= 0) {
            throw new IllegalArgumentException("TaskCount must be positive");
        }
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() throws CantGenerateTask {
        if (isFinished()) {
            throw new QuizIsFinishedException("Quiz is finished!");
        }
        if (needNew) {
            currentTask = generator.generate();
        }
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        if (currentTask == null){
            throw new NoTaskWasGeneratedException("Task wasn't generated yet");
        }
        if (isFinished()) {
            throw new QuizIsFinishedException("Quiz is finished!");
        }
        var tmp = currentTask.validate(answer);
        if (tmp == Result.OK) {
            correctAnswerNumber += 1;
            currentTaskNumber += 1;
            needNew = true;
        } else if (tmp == Result.INCORRECT_INPUT) {
            incorrectInputNumber += 1;
            needNew = false;
        } else if (tmp == Result.WRONG){
            currentTaskNumber += 1;
            needNew = true;
        }
        return tmp;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return currentTaskNumber == taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return currentTaskNumber - correctAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (currentTaskNumber != taskCount) {
            throw new QuizNotFinishedException("Test is not ended!");
        }
        return ((double) correctAnswerNumber) / ((double) taskCount);
    }
}
