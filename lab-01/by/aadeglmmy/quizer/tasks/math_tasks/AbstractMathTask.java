package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.Result;
import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTask implements MathTask {

  private final String text;
  private final double answer;

  public AbstractMathTask(String text, double answer) {
    this.text = text;
    this.answer = answer;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public Result validate(String answer) {
    double userAnswer;
    try {
      userAnswer = Double.parseDouble(answer);
    } catch (NumberFormatException exception) {
      return Result.INCORRECT_INPUT;
    }

    String strAnswer = Double.toString(this.answer);
    int precision = strAnswer.length() - strAnswer.indexOf('.') - 1;

    if (Math.abs(this.answer - userAnswer) < Math.pow(10, -precision) / 2) {
      return Result.OK;
    } else {
      return Result.WRONG;
    }
  }

  protected static abstract class Generator implements MathTask.Generator {

    protected double minNumber;
    protected double maxNumber;
    protected EnumSet<Operation> operations;
    protected int precision;
    protected Random random = new Random();
    protected double precisionFactor;

    protected Generator(double minNumber, double maxNumber, int precision,
        EnumSet<Operation> operations) {
      if (maxNumber < minNumber) {
        throw new IllegalArgumentException("maxNumber cannot be less than minNumber");
      }

      if (operations.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }

      if (precision < 0) {
        throw new IllegalArgumentException("Precision can't be negative");
      }

      this.minNumber = minNumber;
      this.maxNumber = maxNumber;
      this.precision = precision;
      this.operations = operations;
      precisionFactor = Math.pow(10, precision);
    }

    @Override
    public double getMinNumber() {
      return minNumber;
    }

    @Override
    public double getMaxNumber() {
      return maxNumber;
    }

    @Override
    public MathTask generate() {
      return null;
    }

    protected double getRandomNumber() {
      double diff = getDiffNumber();
      double randomNumber = minNumber + diff * random.nextDouble();
      return Math.round(randomNumber * precisionFactor) / precisionFactor;
    }

    protected String getRandomOperator() {
      return null;
    }

    protected double calculateAnswer(double num1, double num2, String operator) {
      double rawAnswer = switch (operator) {
        case "+" -> num1 + num2;
        case "-" -> num1 - num2;
        case "*" -> num1 * num2;
        case "/" -> num1 / num2;
        default -> throw new IllegalArgumentException("Illegal operator: " + operator);
      };
      return Math.round(rawAnswer * precisionFactor) / precisionFactor;
    }
  }
}
