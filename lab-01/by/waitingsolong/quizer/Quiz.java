package by.waitingsolong.quizer;

import by.waitingsolong.quizer.exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private int taskCount;
    private int correctAnsCount = 0;
    private int wrongAnsCount = 0;
    private int incorrectInputCount = 0;

    private boolean tryLastTaskAgain = false;

    private Task lastTask;

    Task.Generator generator;

    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        this.taskCount = taskCount;
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (tryLastTaskAgain) {
            tryLastTaskAgain = false;
            System.out.println("Try again");
            return lastTask;
        }
        Task task = generator.generate();
        lastTask = task;
        return task;
    }

    String getCorrectAnswer() {
        return lastTask.getAnswer();
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result res = lastTask.validate(answer);
        switch(res) {
            case INCORRECT_INPUT -> {
                tryLastTaskAgain = true;
                incorrectInputCount++;
                break;
            }
            case OK -> {
                correctAnsCount++;
                taskCount--;
                break;
            }
            case WRONG -> {
                wrongAnsCount++;
                taskCount--;
                break;
            }
        }
        return res;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        if (taskCount == 0) {
            return true;
        }
        return false;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnsCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongAnsCount;
    }

    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
        return incorrectInputCount;
    }

    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов.
     *         Оценка выставляется только в конце!
     */
    double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinishedException("You tried to get marked from unfinished test");
        }
        return (double) correctAnsCount / (correctAnsCount + wrongAnsCount);
    }
}