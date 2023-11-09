package by.aadeglmmy.quizer.tasks;

import by.aadeglmmy.quizer.Result;
import by.aadeglmmy.quizer.Task;

public class EquationTask implements Task {

  private final String text;
  private final String answer;

  public EquationTask(String text, String answer) {
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
