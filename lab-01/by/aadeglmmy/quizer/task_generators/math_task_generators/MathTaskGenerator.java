package by.aadeglmmy.quizer.task_generators.math_task_generators;

import by.aadeglmmy.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {

  int getMinNumber();

  int getMaxNumber();

  default int getDiffNumber() {
    return getMaxNumber() - getMinNumber();
  }
}
