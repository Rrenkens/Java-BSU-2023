package by.aadeglmmy.quizer.task_generators.math_task_generators;

import by.aadeglmmy.quizer.exceptions.InvalidConfigurationException;
import by.aadeglmmy.quizer.tasks.math_tasks.ExpressionMathTask;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {

  public ExpressionMathTaskGenerator(int minNumber, int maxNumber, boolean generateSum,
      boolean generateDifference, boolean generateMultiplication, boolean generateDivision) {

    super(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication,
        generateDivision);

    if (minNumber == 0 && maxNumber == 0 && !(generateSum || generateDifference
        || generateMultiplication)) {
      throw new InvalidConfigurationException(
          "Invalid configuration: Cannot generate expressions.");
    }
  }

  @Override
  public ExpressionMathTask generate() {
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

    return new ExpressionMathTask(text, String.valueOf(answer));
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
    if (generateMultiplication) {
      operators.append("*");
    }
    if (generateDivision && !(minNumber == 0 && maxNumber == 0)) {
      operators.append("/");
    }

    int randomIndex = random.nextInt(operators.length());
    return String.valueOf(operators.charAt(randomIndex));
  }

  String createExpressionTaskText(int num1, int num2, String operator) {
    return num1 + operator + num2 + "=";
  }
}
