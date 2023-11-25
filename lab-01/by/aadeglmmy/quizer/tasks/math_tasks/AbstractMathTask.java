package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.Result;
import java.util.EnumSet;
import java.util.Random;

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

  protected static abstract class Generator implements MathTask.Generator {

    protected int minNumber;
    protected int maxNumber;
    protected EnumSet<Operation> operations;
    protected Random random = new Random();

    protected Generator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> operations) {
      if (maxNumber < minNumber) {
        throw new IllegalArgumentException("maxNumber cannot be less than minNumber");
      }

      if (operations.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }

      this.minNumber = minNumber;
      this.maxNumber = maxNumber;
      this.operations = operations;
    }

    @Override
    public int getMinNumber() {
      return minNumber;
    }

    @Override
    public int getMaxNumber() {
      return maxNumber;
    }

    @Override
    public MathTask generate() {
      return null;
    }

    protected int getRandomNumber() {
      return random.nextInt(maxNumber - minNumber + 1) + minNumber;
    }

    protected String getRandomOperator() {
      return null;
    }

    protected int calculateAnswer(int num1, int num2, String operator) {
      return switch (operator) {
        case "+" -> num1 + num2;
        case "-" -> num1 - num2;
        case "*" -> num1 * num2;
        case "/" -> num1 / num2;
        default -> throw new IllegalArgumentException("Invalid operator: " + operator);
      };
    }
  }
}
