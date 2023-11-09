package by.aadeglmmy.quizer;

import by.aadeglmmy.quizer.exceptions.InsufficientTasksException;
import by.aadeglmmy.quizer.exceptions.QuizNotFinishedException;
import by.aadeglmmy.quizer.task_generators.GroupTaskGenerator;
import by.aadeglmmy.quizer.task_generators.PoolTaskGenerator;

public class Quiz {

  private final TaskGenerator generator;
  private final int taskCount;
  private int currentTaskIndex = 0;
  private int correctAnswerNumber = 0;
  private int wrongAnswerNumber = 0;
  private int incorrectInputNumber = 0;
  private Task task = null;
  private boolean incorrectInputFlag = false;

  Quiz(TaskGenerator generator, int taskCount) {
    if (taskCount < 1) {
      throw new IllegalArgumentException("There must be at least one task");
    }

    if (generator instanceof PoolTaskGenerator) {
      if (!((PoolTaskGenerator) generator).getAllowDuplicate()
          && ((PoolTaskGenerator) generator).getTasks().size() < taskCount) {
        throw new InsufficientTasksException("Insufficient number of tasks in the pool.");
      }
    }

    this.generator = generator;
    this.taskCount = taskCount;

    if (generator instanceof GroupTaskGenerator) {
      if (countTasksInTheGroup((GroupTaskGenerator) generator) != -1) {
        throw new InsufficientTasksException("Insufficient number of tasks in the group.");
      }
    }
  }

  Task nextTask() {
    if (incorrectInputFlag) {
      incorrectInputFlag = false;
      return task;
    }

    if (currentTaskIndex < taskCount) {
      task = generator.generate();
      ++currentTaskIndex;
      return task;
    }

    return null;
  }

  Result provideAnswer(String answer) {
    if (task == null) {
      return null;
    }

    Result result = task.validate(answer);
    switch (result) {
      case OK:
        ++correctAnswerNumber;
        break;
      case WRONG:
        ++wrongAnswerNumber;
        break;
      case INCORRECT_INPUT:
        ++incorrectInputNumber;
        incorrectInputFlag = true;
        break;
    }
    return result;
  }

  boolean isFinished() {
    return currentTaskIndex >= taskCount && !incorrectInputFlag;
  }

  int getCorrectAnswerNumber() {
    return correctAnswerNumber;
  }

  int getWrongAnswerNumber() {
    return wrongAnswerNumber;
  }

  int getIncorrectInputNumber() {
    return incorrectInputNumber;
  }

  double getMark() {
    if (!isFinished()) {
      throw new QuizNotFinishedException("Quiz is not finished yet.");
    }
    return (double) correctAnswerNumber / taskCount;
  }


  private int countTasksInTheGroup(GroupTaskGenerator generator) {
    int count = 0;
    for (TaskGenerator subGenerator : generator.getGenerators()) {
      if (subGenerator instanceof PoolTaskGenerator) {
        if (((PoolTaskGenerator) subGenerator).getAllowDuplicate()) {
          return -1;
        } else {
          count += ((PoolTaskGenerator) subGenerator).getTasks().size();
        }
      } else if (subGenerator instanceof GroupTaskGenerator) {
        int subCount = countTasksInTheGroup((GroupTaskGenerator) subGenerator);
        if (subCount == -1) {
          return -1;
        }
        count += subCount;
      } else {
        return -1;
      }
      if (count >= taskCount) {
        return -1;
      }
    }

    return count;
  }
}
