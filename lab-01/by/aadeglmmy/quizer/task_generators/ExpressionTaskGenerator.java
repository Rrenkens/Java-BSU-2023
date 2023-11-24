package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.TaskGenerator;
import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import by.aadeglmmy.quizer.tasks.ExpressionTask;
import by.aadeglmmy.quizer.tasks.math_tasks.MathTask;
import by.aadeglmmy.quizer.tasks.math_tasks.MathTask.Operation;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionTaskGenerator implements TaskGenerator {

  private final int minNumber;
  private final int maxNumber;
  private final EnumSet<Operation> operations;
  private final Random random = new Random();

  public ExpressionTaskGenerator(int minNumber, int maxNumber,
      EnumSet<MathTask.Operation> operations) {
    if (maxNumber < minNumber) {
      throw new IllegalArgumentException("maxNumber cannot be less than minNumber");
    }

    if (operations.isEmpty()) {
      throw new UnsupportedOperationException("No operations selected");
    }

    if (minNumber == 0 && maxNumber == 0 && !(operations.contains(Operation.SUM)
        || operations.contains(Operation.DIFFERENCE) || operations.contains(
        Operation.MULTIPLICATION))) {
      throw new InvalidConfigurationException(
          "Invalid configuration: Cannot generate expressions.");
    }

    this.minNumber = minNumber;
    this.maxNumber = maxNumber;
    this.operations = operations;
  }

  @Override
  public ExpressionTask generate() {
    String operator = getRandomOperator();
    int num1 = getRandomNumber();
    int num2 = getRandomNumber();
    if (operator.equals("/")) {
      while (num2 == 0) {
        num2 = getRandomNumber();
      }
    }
    int answer = calculateAnswer(num1, num2, operator);
    String text;
    if (operator.equals("/")) {
      text = createTaskText(num2 * answer, num2, operator);
    } else {
      text = createTaskText(num1, num2, operator);
    }

    return new ExpressionTask(text, String.valueOf(answer));
  }

  private int getRandomNumber() {
    return random.nextInt(maxNumber - minNumber + 1) + minNumber;
  }

  private String getRandomOperator() {
    StringBuilder operators = new StringBuilder();
    if (operations.contains(Operation.SUM)) {
      operators.append("+");
    }
    if (operations.contains(Operation.DIFFERENCE)) {
      operators.append("-");
    }
    if (operations.contains(Operation.MULTIPLICATION)) {
      operators.append("*");
    }
    if (operations.contains(Operation.DIVISION) && !(minNumber == 0 && maxNumber == 0)) {
      operators.append("/");
    }
    if (operators.isEmpty()) {
      throw new UnsupportedOperationException("No operations selected");
    }
    int randomIndex = random.nextInt(operators.length());
    return String.valueOf(operators.charAt(randomIndex));
  }

  private int calculateAnswer(int num1, int num2, String operator) {
    return switch (operator) {
      case "+" -> num1 + num2;
      case "-" -> num1 - num2;
      case "*" -> num1 * num2;
      case "/" -> num1 / num2;
      default -> throw new IllegalArgumentException("Invalid operator: " + operator);
    };
  }

  private String createTaskText(int num1, int num2, String operator) {
    return num1 + operator + num2 + "=";
  }
}
