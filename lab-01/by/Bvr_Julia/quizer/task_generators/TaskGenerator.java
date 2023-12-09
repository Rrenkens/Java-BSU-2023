package by.Bvr_Julia.quizer.task_generators;

import by.Bvr_Julia.quizer.tasks.Task;

/**
 * Interface, which describes one task generator
 */
public interface TaskGenerator {
    /**
     * Returns the task. In this case, a new object may not be created if the job class is immutable
     *       *
     *       * @return task
     * @see    Task
     */
    Task generate();
}
