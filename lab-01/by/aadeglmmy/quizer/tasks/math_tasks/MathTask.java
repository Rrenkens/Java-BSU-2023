package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.Task;

public interface MathTask extends Task {

  enum Operation {
    SUM, DIFFERENCE, MULTIPLICATION, DIVISION
  }

  interface Generator extends Task.Generator {

    double getMinNumber();

    double getMaxNumber();

    default double getDiffNumber() {
      return getMaxNumber() - getMinNumber();
    }
  }
}
