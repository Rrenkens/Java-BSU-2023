package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
      Operation operator = getRandomOperator();
      double num1 = getRandomNumber();
      double num2 = getRandomNumber();

      if (operator.equals(Operation.DIVISION)) {
        while (num2 == 0) {
          num2 = getRandomNumber();
        }
      }
      double answer = calculateAnswer(num1, num2, operator);
      String text = createExpressionTaskText(num1, num2, operator);
      return new ExpressionTask(text, answer);
    }

    @Override
    protected Operation getRandomOperator() {
      List<Operation> operators = new ArrayList<>();
      if (operations.contains(Operation.SUM)) {
        operators.add(Operation.SUM);
      }
      if (operations.contains(Operation.DIFFERENCE)) {
        operators.add(Operation.DIFFERENCE);
      }
      if (operations.contains(Operation.MULTIPLICATION)) {
        operators.add(Operation.MULTIPLICATION);
      }
      double compared = 1 / precisionFactor;
      if (operations.contains(Operation.DIVISION) && !(Math.abs(minNumber) < compared
          && Math.abs(maxNumber) < compared)) {
        operators.add(Operation.DIVISION);
      }
      if (operators.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }
      int randomIndex = random.nextInt(operators.size());
      return operators.get(randomIndex);
    }

    String createExpressionTaskText(double num1, double num2, Operation operator) {
      String strOperator = null;
      switch (operator) {
        case SUM -> strOperator = "+";
        case DIFFERENCE -> strOperator = "-";
        case MULTIPLICATION -> strOperator = "*";
        case DIVISION -> strOperator = "/";
      }
      return num1 + strOperator + num2 + "=";
    }
  }
}
