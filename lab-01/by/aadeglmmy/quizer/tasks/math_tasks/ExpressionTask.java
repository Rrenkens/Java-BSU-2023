package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {

  public ExpressionTask(String text, double answer) {
    super(text, answer);
  }

  static public class Generator extends AbstractMathTask.Generator {

    public Generator(double minNumber, double maxNumber, int precision,
        EnumSet<Operation> operations) {
      super(minNumber, maxNumber, precision, operations);
      if (Math.abs(minNumber) < Math.pow(10, -precision) && Math.abs(maxNumber) < Math.pow(10,
          -precision) && !(operations.contains(Operation.SUM) || operations.contains(
          Operation.DIFFERENCE) || operations.contains(Operation.MULTIPLICATION))) {
        throw new InvalidConfigurationException(
            "Invalid configuration: Cannot generate expressions.");
      }
    }

    public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
      this(minNumber, maxNumber, 0, operations);
    }

    @Override
    public ExpressionTask generate() {
      String operator = getRandomOperator();
      double num1 = getRandomNumber();
      double num2 = getRandomNumber();

      if (operator.equals("/")) {
        while (num2 == 0) {
          num2 = getRandomNumber();
        }
      }
      double answer = calculateAnswer(num1, num2, operator);
      String text = createExpressionTaskText(num1, num2, operator);
      return new ExpressionTask(text, answer);
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
      double compared = 1 / precisionFactor;
      if (operations.contains(Operation.DIVISION) && !(Math.abs(minNumber) < compared
          && Math.abs(maxNumber) < compared)) {
        operators.append("/");
      }
      if (operators.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }
      int randomIndex = random.nextInt(operators.length());
      return String.valueOf(operators.charAt(randomIndex));
    }

    String createExpressionTaskText(double num1, double num2, String operator) {
      return num1 + operator + num2 + "=";
    }
  }
}
