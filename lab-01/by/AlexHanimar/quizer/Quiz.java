package by.AlexHanimar.quizer;

import by.AlexHanimar.quizer.exceptions.CheckException;
import by.AlexHanimar.quizer.exceptions.NoNextTaskException;
import by.AlexHanimar.quizer.exceptions.QuizNotFinishedException;
import by.AlexHanimar.quizer.exceptions.TaskGenerationException;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private TaskGenerator generator;
    private final int taskCount;
    private int curCount = 0;
    private Task last = null;
    private boolean genNew = true;

    private int ok = 0;
    private int wrong = 0;
    private int incorrect = 0;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    public Quiz(TaskGenerator generator, int taskCount) throws IllegalArgumentException {
        // ...
        if (generator == null || taskCount <= 0)
            throw new IllegalArgumentException();
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() throws NoNextTaskException {
        if (isFinished())
            throw new NoNextTaskException();
        if (!genNew) {
            genNew = true;
            return last;
        }
        if (curCount >= taskCount)
            throw new NoNextTaskException();
        try {
            ++curCount;
            last = generator.generate();
            return last;
        } catch (TaskGenerationException ex) {
            --curCount;
            throw new NoNextTaskException();
        }
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) throws CheckException {
        if (last == null)
            throw new CheckException();
        var res = last.validate(answer);
        switch (res) {
            case OK -> {
                ok++;
            } case WRONG -> {
                wrong++;
            } case INCORRECT_INPUT -> {
                incorrect++;
                genNew = false;
            }
        }
        return res;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return curCount >= taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return ok;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrong;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrect;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() throws QuizNotFinishedException {
        if (!isFinished())
            throw new QuizNotFinishedException();
        if (curCount == 0)
            return 1.0;
        double ratio = ok;
        ratio /= curCount;
        return ratio;
    }
}
