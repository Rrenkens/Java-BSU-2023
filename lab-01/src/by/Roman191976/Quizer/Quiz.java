package by.Roman191976.Quizer;

import java.util.ArrayList;
import java.util.List;

import by.Roman191976.Quizer.Exceptions.QuizCannotBeGenerated;
import by.Roman191976.Quizer.Exceptions.QuizNotFinishedException;

/**
 * Class, который описывает один тест
 */
public class Quiz {
    private int taskCount;
    private List<Task> tasks;
    private int currentIndex;
    private int correctAnswerCount;
    private int wrongAnswerCount;
    private int incorrectInputCount;
    private Task currentTask;
    Result currentResult = Result.INCORRECT_INPUT;


    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(TaskGenerator generator, int taskCount) {
        this.taskCount = taskCount;
        tasks = new ArrayList<>();
        currentIndex = 0;
        correctAnswerCount = 0;
        wrongAnswerCount = 0;
        incorrectInputCount = 0;
        currentTask = null;

        try {
                    for (int i = 0; i < taskCount; i++) {
            tasks.add(generator.generate());
        } 
    } catch (Exception e) {
        throw new QuizCannotBeGenerated("ошибка при создании теста");
    }

    }


    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (currentIndex >= taskCount) {
            return null;
        }

        if (currentTask != null && currentResult == Result.INCORRECT_INPUT) {
            return currentTask;
        }

        currentTask = tasks.get(currentIndex);
        currentIndex++;
        return currentTask;
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных 
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        if (currentTask == null) {
            return null;
        }

        currentResult = currentTask.validate(answer);

        switch (currentResult) {
            case OK:
                correctAnswerCount++;
                break;
            case WRONG:
                wrongAnswerCount++;
                break;
            case INCORRECT_INPUT:
                incorrectInputCount++;
                break;
        }

        return currentResult;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return currentIndex >= taskCount;
    }

    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnswerCount;
    }

    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongAnswerCount;
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
        if (!isFinished()) throw new QuizNotFinishedException("тест не оконче!!!");
        if (taskCount == 0) {
            return 0.0;
        }
        return (double) correctAnswerCount / taskCount;
    }
}