package by.lamposhka.quizer;

import java.util.ArrayList;

/**
 * Class, который описывает один тест
 */
class Quiz {
    private final ArrayList<Task> tasks;
    private int mistakesCount = 0;
    private int incorrectInputCount = 0;
    private int currentTaskIndex = 0;
    private boolean taskSwitchIndicator = false;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(TaskGenerator generator, int taskCount) {
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
            if (taskSwitchIndicator) {
                ++currentTaskIndex;
                return tasks.get(currentTaskIndex); // :o
            } else {
                taskSwitchIndicator = true;
                return tasks.get(currentTaskIndex);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        switch (tasks.get(currentTaskIndex).validate(answer)) {
            case OK:
                break;
            case WRONG:
                ++mistakesCount;
                break;
            default:
                ++incorrectInputCount;
                taskSwitchIndicator = false;
                break;
        }
        return tasks.get(currentTaskIndex).validate(answer);
    }

    /**
     * @return завершен ли тест
     */
    public boolean isFinished() {
        if (currentTaskIndex < tasks.size() - 1) {
            return false;
        }
        return true;
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
    double getMark() {
        if (isFinished()) {
            return 100 * ((double) (tasks.size() - mistakesCount)) / tasks.size();
        }
        return 0;
    }
}
