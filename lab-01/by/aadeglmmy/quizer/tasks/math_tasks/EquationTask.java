package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {

  public EquationTask(String text, double answer) {
    super(text, answer);
  }

  static public class Generator extends AbstractMathTask.Generator {

    public Generator(double minNumber, double maxNumber, int precision,
        EnumSet<Operation> operations) {
      super(minNumber, maxNumber, precision, operations);

      if (Math.abs(minNumber) < Math.pow(10, -precision) && Math.abs(maxNumber) < Math.pow(10,
          -precision) && !(operations.contains(Operation.SUM) || operations.contains(
          Operation.DIFFERENCE))) {
        throw new InvalidConfigurationException(
            "Invalid configuration: Cannot generate equations.");
      }
    }

    public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations) {
      this(minNumber, maxNumber, 0, operations);
    }

    @Override
    public EquationTask generate() {
      String operator = getRandomOperator();
      double num1 = getRandomNumber();
      double num2 = getRandomNumber();
      if (operator.equals("/")) {
        while (num2 == 0) {
          num2 = getRandomNumber();
        }
      }

      boolean variation = random.nextBoolean();
      if (operator.equals("*")) {
        if (variation) {
          while (num1 == 0) {
            num1 = getRandomNumber();
          }
        } else {
          while (num2 == 0) {
            num2 = getRandomNumber();
          }
        }
      }
      if (operator.equals("/") && variation) {
        while (Math.abs(num1) < Math.abs(num2) || num2 == 0) {
          num1 = getRandomNumber();
          num2 = getRandomNumber();
        }
      }
      double answer = calculateAnswer(num1, num2, operator);
      String text = createEquationTaskText(num1, num2, operator, answer, variation);
      if (variation) {
        if (operator.equals("/")) {
          answer = num1 / answer;
        } else {
          answer = num2;
        }
      } else {
        if (operator.equals("/")) {
          answer = answer * num2;
        } else {
          answer = num1;
        }
      }
      return new EquationTask(text, Math.round(answer * precisionFactor) / precisionFactor);
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
      double compared = 1 / precisionFactor;
      if (!(Math.abs(minNumber) < compared && Math.abs(maxNumber) < compared)) {
        if (operations.contains(Operation.MULTIPLICATION)) {
          operators.append("*");
        }
        if (operations.contains(Operation.DIVISION)) {
          operators.append("/");
        }
      }
      if (operators.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }
      int randomIndex = random.nextInt(operators.length());
      return String.valueOf(operators.charAt(randomIndex));
    }

    String createEquationTaskText(double num1, double num2, String operator, double answer,
        boolean variation) {
      String text;
      if (variation) {
        text = num1 + operator + "x=" + answer;
      } else {
        text = "x" + operator + num2 + "=" + answer;
      }
      return text;
    }
  }
}
