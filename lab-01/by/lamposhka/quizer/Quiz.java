package by.lamposhka.quizer;

import by.lamposhka.quizer.exceptions.QuizNotFinishedException;
import by.lamposhka.quizer.tasks.Task;

import java.util.ArrayList;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private final ArrayList<Task> tasks;
    private int mistakesCount = 0;
    private int incorrectInputCount = 0;
    private int currentTaskIndex = 0;
    private int lastValidatedTaskIndex = -1;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(Task.Generator generator, int taskCount) throws Exception {
        tasks = new ArrayList<>(taskCount);
        for (int i = 0; i < taskCount; ++i) {
            tasks.add(generator.generate());
        }
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    Task nextTask() {
        if (!isFinished()) {
            if (lastValidatedTaskIndex == currentTaskIndex) {
                ++currentTaskIndex;
            }
            return tasks.get(currentTaskIndex); // o_O
        } else {
            throw new IndexOutOfBoundsException("Trying to get the next task when the quiz is already finished.");
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Task.Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Task.Result provideAnswer(String answer) {
        switch (tasks.get(currentTaskIndex).validate(answer)) {
            case OK:
                ++lastValidatedTaskIndex;
                break;
            case WRONG:
                ++lastValidatedTaskIndex;
                ++mistakesCount;
                break;
            default:
                ++incorrectInputCount;
                break;
        }
        return tasks.get(currentTaskIndex).validate(answer);
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        return lastValidatedTaskIndex == tasks.size() - 1;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return tasks.size() - mistakesCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return mistakesCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     * Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (isFinished()) {
            return 100 * ((double) (tasks.size() - mistakesCount)) / tasks.size();
        }
        throw new QuizNotFinishedException("Trying to call getMark() when the quiz is not finished.");
    }
}
