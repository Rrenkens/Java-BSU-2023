package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.Result;

public abstract class AbstractMathTask implements MathTask {

  private final String text;
  private final String answer;

  public AbstractMathTask(String text, String answer) {
    this.text = text;
    this.answer = answer;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public Result validate(String answer) {
    if (answer == null || answer.isEmpty()) {
      return Result.INCORRECT_INPUT;
    }

    if (this.answer.equals(answer)) {
      return Result.OK;
    } else {
      return Result.WRONG;
    }
  }
}
