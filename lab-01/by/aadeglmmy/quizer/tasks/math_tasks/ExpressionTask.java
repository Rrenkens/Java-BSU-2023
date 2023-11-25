package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {

  public ExpressionTask(String text, String answer) {
    super(text, answer);
  }

  static public class Generator extends AbstractMathTask.Generator {

    public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {

      super(minNumber, maxNumber, operations);

      if (minNumber == 0 && maxNumber == 0 && !(operations.contains(Operation.SUM)
          || operations.contains(Operation.DIFFERENCE) || operations.contains(
          Operation.MULTIPLICATION))) {
        throw new InvalidConfigurationException(
            "Invalid configuration: Cannot generate expressions.");
      }
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
        text = createExpressionTaskText(num2 * answer, num2, operator);
      } else {
        text = createExpressionTaskText(num1, num2, operator);
      }

      return new ExpressionTask(text, String.valueOf(answer));
    }

    @Override
    protected String getRandomOperator() {
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

    String createExpressionTaskText(int num1, int num2, String operator) {
      return num1 + operator + num2 + "=";
    }
  }
}
