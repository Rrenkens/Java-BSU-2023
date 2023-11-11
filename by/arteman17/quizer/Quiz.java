package by.arteman17.quizer;

import by.arteman17.quizer.exceptions.QuizFinished;
import by.arteman17.quizer.exceptions.QuizNotFinished;
/**
 * Class, который описывает один тест
 */
public class Quiz {
    private final TaskGenerator generator_;
    final int taskCount_;
    private Task curr_;
    private int total_ = 0;
    private int correct_ = 0;
    private int incorrect_ = 0;
    boolean isTaskNeeded = true;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        if (generator == null) {
            throw new IllegalArgumentException("Generator is null");
        }
        generator_ = generator;
        if (taskCount <= 0) {
            throw new IllegalArgumentException("TaskCount must be positive");
        }
        taskCount_ = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (isFinished()) {
            throw new QuizFinished("Quiz is finished!");
        }
        if (isTaskNeeded) {
            curr_ = generator_.generate();
        }
        return curr_;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        if (curr_ == null) {
            throw new RuntimeException("No task was generated");
        }
        if (isFinished()) {
            throw new QuizFinished("Quiz is finished!");
        }
        Result ans = curr_.validate(answer);
        if (ans != Result.INCORRECT_INPUT) {
            if (ans == Result.OK) {
                ++correct_;
            }
            ++total_;
            isTaskNeeded = true;
        } else {
            ++incorrect_;
            isTaskNeeded = false;
        }
        return ans;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return taskCount_ == total_;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correct_;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return total_ - correct_;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrect_;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinished("Quiz is not finished!");
        }
        return ((double) correct_ / total_) * 10;
    }
}