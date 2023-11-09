package by.aadeglmmy.quizer.task_generators.math_task_generators;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import by.aadeglmmy.quizer.tasks.math_tasks.EquationMathTask;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {

  public EquationMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum,
      boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
    super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication,
        generateDivision);

    if (minNumber == 0 && maxNumber == 0 && !(generateDifference || generateSum)) {
      throw new InvalidConfigurationException("Invalid configuration: Cannot generate equations.");
    }
  }

  @Override
  public EquationMathTask generate() {

    //TODO: nice checks

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
      return new EquationMathTask(text, String.valueOf(num2));
    } else {
      return new EquationMathTask(text, String.valueOf(num1));
    }
  }

  @Override
  protected String getRandomOperator() {
    StringBuilder operators = new StringBuilder();
    if (generateSum) {
      operators.append("+");
    }
    if (generateDifference) {
      operators.append("-");
    }
    if (!(minNumber == 0 && maxNumber == 0)) {
      if (generateMultiplication) {
        operators.append("*");
      }
      if (generateDivision) {
        operators.append("/");
      }
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

