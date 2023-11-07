package by.katierevinska.quizer;
import by.katierevinska.quizer.exceptions.QuizFinishedException;
import by.katierevinska.quizer.exceptions.QuizNotFinishedException;

import java.util.ArrayList;

public class Quiz {
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    private int taskCount;
    private int currentIndex;
    private int correctAnswerNumber=0;
    private int wrongAnswerNumber=0;
    private int incorrectInputNumber=0;
    private double mark;
    private ArrayList<Task> test = new ArrayList<>();

    Quiz(Task.Generator generator, int taskCount) {
        if(taskCount == 0){
            throw new IllegalArgumentException("number of tasks should be more than 0");
        }
        this.taskCount = taskCount;
        this.currentIndex = 0;
        for(int i = 0; i < taskCount; i++){
            test.add(generator.generate());
        }
    }

    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
        if (isFinished()) {
            throw new QuizFinishedException("Test already finished , you can't get nextTask");
        }
        return test.get(currentIndex);
    }

    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result tmp = this.test.get(currentIndex).validate(answer);
        if(tmp == Result.OK){
            correctAnswerNumber++;
            currentIndex ++;
        }
        else if(tmp == Result.WRONG){
            this.wrongAnswerNumber++;
            currentIndex ++;
        }
        else {
            this.incorrectInputNumber++;
        }
        return tmp;
    }

    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return currentIndex > taskCount-1;
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
    double getMark() {
        if (!isFinished()) {
            throw new QuizNotFinishedException("Test doesn't finished yet, you can't get mark");
        }
        mark = ((double)correctAnswerNumber)/taskCount;
        return mark;
    }
}
