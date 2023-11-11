package by.nrydo.quizer;

import by.nrydo.quizer.exceptions.NoTasksException;
import by.nrydo.quizer.exceptions.QuizNotFinishedException;
import by.nrydo.quizer.exceptions.TaskGenerationException;

/**
 * Class, который описывает один тест
 */
class Quiz {

    private final TaskGenerator generator;
    private int taskCount;
    private Task currentTask = null;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;
    private boolean canGenerate = true;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        if (taskCount == 0) {
            throw new IllegalArgumentException();
        }
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    Task nextTask() throws TaskGenerationException, NoTasksException {
        if (taskCount == 0) {
            throw new NoTasksException();
        }
        if (canGenerate) {
            currentTask = generator.generate();
            canGenerate = false;
            return currentTask;
        } else {
            return currentTask;
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        var result = currentTask.validate(answer);
        switch (result) {
            case OK:
                correctAnswerNumber++;
                canGenerate = true;
                taskCount--;
                break;
            case WRONG:
                wrongAnswerNumber++;
                canGenerate = true;
                taskCount--;
                break;
            case INCORRECT_INPUT:
                incorrectInputNumber++;
                break;
        }
        return result;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return taskCount == 0;
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
        return wrongAnswerNumber;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (taskCount != 0) {
            throw new QuizNotFinishedException();
        }
        double done = correctAnswerNumber + wrongAnswerNumber;
        return correctAnswerNumber / done;
    }
}
