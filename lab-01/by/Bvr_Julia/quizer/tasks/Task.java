package by.Bvr_Julia.quizer.tasks;

import by.Bvr_Julia.quizer.Result;

/**
 * Interface, which describes one task
 */
public interface Task {
    /**
     @return текст задания
     */
    public String getText();

    /**
     * Checks the answer to a task and returns the result
     *       *
     *       * @param answer the answer to the task
     *       * @return the result of the response
     *       * @see Result
     */
    public Result validate(String answer);
}