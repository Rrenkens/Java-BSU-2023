package by.aadeglmmy.quizer.tasks.math_tasks;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import by.aadeglmmy.quizer.tasks.math_tasks.AbstractMathTask.Generator;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
      Operation operator = getRandomOperator();
      double num1 = getRandomNumber();
      double num2 = getRandomNumber();
      if (operator.equals(Operation.DIVISION)) {
        while (num2 == 0) {
          num2 = getRandomNumber();
        }
      }

      boolean variation = random.nextBoolean();
      if (operator.equals(Operation.MULTIPLICATION)) {
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
      if (operator.equals(Operation.DIVISION) && variation) {
        while (Math.abs(num1) < Math.abs(num2) || num2 == 0) {
          num1 = getRandomNumber();
          num2 = getRandomNumber();
        }
      }
      double answer = calculateAnswer(num1, num2, operator);
      String text = createEquationTaskText(num1, num2, operator, answer, variation);
      if (variation) {
        if (operator.equals(Operation.DIVISION)) {
          answer = num1 / answer;
        } else {
          answer = num2;
        }
      } else {
        if (operator.equals(Operation.DIVISION)) {
          answer = answer * num2;
        } else {
          answer = num1;
        }
      }
      return new EquationTask(text, Math.round(answer * precisionFactor) / precisionFactor);
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
      double compared = 1 / precisionFactor;
      if (!(Math.abs(minNumber) < compared && Math.abs(maxNumber) < compared)) {
        if (operations.contains(Operation.MULTIPLICATION)) {
          operators.add(Operation.MULTIPLICATION);
        }
        if (operations.contains(Operation.DIVISION)) {
          operators.add(Operation.DIVISION);
        }
      }
      if (operators.isEmpty()) {
        throw new UnsupportedOperationException("No operations selected");
      }
      int randomIndex = random.nextInt(operators.size());
      return operators.get(randomIndex);
    }

    String createEquationTaskText(double num1, double num2, Operation operator, double answer,
        boolean variation) {
      String strOperator = null;
      switch (operator) {
        case SUM -> strOperator = "+";
        case DIFFERENCE -> strOperator = "-";
        case MULTIPLICATION -> strOperator = "*";
        case DIVISION -> strOperator = "/";
      }
      String text;
      if (variation) {
        text = num1 + strOperator + "x=" + answer;
      } else {
        text = "x" + strOperator + num2 + "=" + answer;
      }
      return text;
    }
  }
}
