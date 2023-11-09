package by.aadeglmmy.quizer.task_generators.math_task_generators;

import by.aadeglmmy.quizer.tasks.math_tasks.MathTask;
import java.util.Random;

abstract class AbstractMathTaskGenerator implements MathTaskGenerator {

  protected int minNumber;
  protected int maxNumber;
  protected boolean generateSum;
  protected boolean generateDifference;
  protected boolean generateMultiplication;
  protected boolean generateDivision;
  protected Random random = new Random();

  protected AbstractMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum,
      boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {
    if (maxNumber < minNumber) {
      throw new IllegalArgumentException("maxNumber cannot be less than minNumber");
    }

    if (!(generateSum || generateDifference || generateMultiplication || generateDivision)) {
      throw new UnsupportedOperationException("No operations selected");
    }

    this.minNumber = minNumber;
    this.maxNumber = maxNumber;
    this.generateSum = generateSum;
    this.generateDifference = generateDifference;
    this.generateMultiplication = generateMultiplication;
    this.generateDivision = generateDivision;
  }

  @Override
  public int getMinNumber() {
    return minNumber;
  }

  @Override
  public int getMaxNumber() {
    return maxNumber;
  }

  @Override
  public MathTask generate() {
    return null;
  }

  protected int getRandomNumber() {
    return random.nextInt(maxNumber - minNumber + 1) + minNumber;
  }

  protected String getRandomOperator() {
    return null;
  }

  protected int calculateAnswer(int num1, int num2, String operator) {
    return switch (operator) {
      case "+" -> num1 + num2;
      case "-" -> num1 - num2;
      case "*" -> num1 * num2;
      case "/" -> num1 / num2;
      default -> throw new IllegalArgumentException("Invalid operator: " + operator);
    };
  }
}
