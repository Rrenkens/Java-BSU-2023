package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.Task;

public interface MathTask extends Task {

  enum Operation {
    SUM, DIFFERENCE, MULTIPLICATION, DIVISION
  }

  interface Generator extends Task.Generator {

    int getMinNumber();

    int getMaxNumber();

    default int getDiffNumber() {
      return getMaxNumber() - getMinNumber();
    }
  }
}
