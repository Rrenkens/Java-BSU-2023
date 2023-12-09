package by.Bvr_Julia.quizer;

import by.Bvr_Julia.quizer.exeptions.QuizExtraTaskException;
import by.Bvr_Julia.quizer.exeptions.QuizNotFinishedException;
import by.Bvr_Julia.quizer.task_generators.TaskGenerator;
import by.Bvr_Julia.quizer.tasks.Task;

import static by.Bvr_Julia.quizer.Result.*;

/**
 * Class, which describes one test
 */
class Quiz {
    /**
     * @param generator task generator
     * @param taskCount number of tasks in the test
     */
    final public int taskCount;
    final private TaskGenerator generator;
    private int incorrectAnswers = 0;
    private int correctAnswers = 0;
    private int incorrectInputs = 0;
    private Task currentTask;
    private boolean incorrect = false;
    Quiz(TaskGenerator generator, int taskCount) {
        this.taskCount = taskCount;
        this.generator = generator;
    }

    /**
     * @return task, calling it again will return the next
     * @see Task
     */
    Task nextTask() {
        if (correctAnswers + incorrectAnswers != taskCount){
            if (!incorrect){
                currentTask = generator.generate(); //!!!
            }
            return currentTask;
        } else{
            throw new QuizExtraTaskException();
        }
    }

    /**
     * Provide student response. If the result is {@link Result#INCORRECT_INPUT}, then the counter of incorrect
     * responses are not increased, and {@link #nextTask()} will return the same {@link Task} object next time.
     */
    Result provideAnswer(String answer) {
        Result res =  currentTask.validate(answer); //!!!
        if (res ==  INCORRECT_INPUT){
            incorrect = true;
            incorrectInputs++;
        }else if (res ==  OK){
            incorrect = false;
            correctAnswers++;
        }else if (res == WRONG){
            incorrect = false;
            incorrectAnswers++;
        }
        return res;
    }

    /**
     * @return is the test completed
     */
    boolean isFinished() {
        return correctAnswers + incorrectAnswers == taskCount;
    }

    /**
     * @return number of correct answers
     */
    int getCorrectAnswerNumber() {
        return correctAnswers;
    }

    /**
     * @return number of incorrect answers
     */
    int getWrongAnswerNumber() {
        return incorrectAnswers;
    }

    /**
     * @return number of times incorrect input was provided
     */
    int getIncorrectInputNumber() {
        return incorrectInputs;
    }

    /**
     * @return score, which is the ratio of the number of correct answers to the number of all questions.
     *        The rating is given only at the end!
     */
    double getMark() {
        if (!isFinished()){
            throw new QuizNotFinishedException();
        }else{
            double res = (double)(correctAnswers);
            res /= taskCount;
            res *= 10;
            return res;
        }
    }
}