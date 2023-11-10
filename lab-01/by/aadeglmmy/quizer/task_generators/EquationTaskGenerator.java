package by.aadeglmmy.quizer.task_generators;

import by.aadeglmmy.quizer.TaskGenerator;
import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import by.aadeglmmy.quizer.tasks.EquationTask;
import java.util.Random;

public class EquationTaskGenerator implements TaskGenerator {

  private final int minNumber;
  private final int maxNumber;
  private final boolean generateSum;
  private final boolean generateDifference;
  private final boolean generateMultiplication;
  private final boolean generateDivision;
  private final Random random = new Random();

  public EquationTaskGenerator(int minNumber, int maxNumber, boolean generateSum,
      boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
    if (maxNumber < minNumber) {
      throw new IllegalArgumentException("maxNumber cannot be less than minNumber");
    }

    if (!(generateSum || generateDifference || generateMultiplication || generateDivision)) {
      throw new UnsupportedOperationException("No operations selected");
    }

    if (minNumber == 0 && maxNumber == 0 && !(generateDifference || generateSum)) {
      throw new InvalidConfigurationException("Invalid configuration: Cannot generate equations.");
    }

    this.minNumber = minNumber;
    this.maxNumber = maxNumber;
    this.generateSum = generateSum;
    this.generateDifference = generateDifference;
    this.generateMultiplication = generateMultiplication;
    this.generateDivision = generateDivision;
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

    String text = createTaskText(num1, num2, operator, answer, variation);

    if (variation) {
      return new EquationTask(text, String.valueOf(num2));
    } else {
      return new EquationTask(text, String.valueOf(num1));
    }
  }

  private int getRandomNumber() {
    return random.nextInt(maxNumber - minNumber + 1) + minNumber;
  }

  private String getRandomOperator() {
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

  private int calculateAnswer(int num1, int num2, String operator) {
    return switch (operator) {
      case "+" -> num1 + num2;
      case "-" -> num1 - num2;
      case "*" -> num1 * num2;
      case "/" -> num1 / num2;
      default -> throw new IllegalArgumentException("Invalid operator: " + operator);
    };
  }

  private String createTaskText(int num1, int num2, String operator, int answer,
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
