package by.fact0rial.quizer;

import by.fact0rial.quizer.exceptions.IllegalNextTaskException;
import by.fact0rial.quizer.exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    final private int taskCount;
    private int currentNumber = 0;
    final private Task.Generator generator;
    private int correct = 0;
    private int wrong = 0;
    private int incorrect = 0;

    private boolean generate = true;
    Task current;
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
        if (taskCount <= 0) {
            throw new IllegalArgumentException("number of tasks should be positive");
        }
    }

    /**
     * @return задание, повторный вызов вернет следующее
     * @see Task
     */
    Task nextTask() throws IllegalNextTaskException {
        if (isFinished()) {
            throw new IllegalNextTaskException("Trying to get next task in a finished quiz");
        }
        if (generate) {
            current = generator.generate();
            currentNumber++;
        }
        return current;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result val = current.validate(answer);
        switch(val) {
            case OK -> {
                correct++;
                generate = true;
            }
            case WRONG -> {
                wrong++;
                generate = true;
            }
            case INCORRECT_INPUT -> {
                incorrect++;
                generate = false;
            }
        }
        return val;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return this.currentNumber >= this.taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return this.correct;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return this.wrong;
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
        if (!isFinished()) {
            throw new QuizNotFinishedException("trying to get mark before finishing");
        }
        return (double) correct / taskCount;
    }
}