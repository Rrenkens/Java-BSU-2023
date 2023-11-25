package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {

  public EquationTask(String text, String answer) {
    super(text, answer);
  }

  static public class Generator extends AbstractMathTask.Generator {

    public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
      super(minNumber, maxNumber, operations);

      if (minNumber == 0 && maxNumber == 0 && !(operations.contains(Operation.SUM)
          || operations.contains(Operation.DIFFERENCE))) {
        throw new InvalidConfigurationException(
            "Invalid configuration: Cannot generate equations.");
      }
    }

    @Override
    public EquationTask generate() {
      String operator = getRandomOperator();
      int num1 = getRandomNumber();
      int num2 = getRandomNumber();
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
      int answer = calculateAnswer(num1, num2, operator);
      if (operator.equals("/")) {
        num1 = answer * num2;
      }

      String text = createEquationTaskText(num1, num2, operator, answer, variation);

      if (variation) {
        return new EquationTask(text, String.valueOf(num2));
      } else {
        return new EquationTask(text, String.valueOf(num1));
      }
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
      if (!(minNumber == 0 && maxNumber == 0)) {
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

    String createEquationTaskText(int num1, int num2, String operator, int answer,
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
