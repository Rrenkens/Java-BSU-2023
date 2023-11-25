package by.aadeglmmy.quizer;

import by.aadeglmmy.quizer.task_generators.GroupTaskGenerator;
import by.aadeglmmy.quizer.task_generators.PoolTaskGenerator;
import by.aadeglmmy.quizer.tasks.TextTask;
import by.aadeglmmy.quizer.tasks.math_tasks.EquationTask;
import by.aadeglmmy.quizer.tasks.math_tasks.ExpressionTask;
import by.aadeglmmy.quizer.tasks.math_tasks.MathTask.Operation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Quizer {

  static Map<String, Quiz> getQuizMap() {
    Map<String, Quiz> quizMap = new HashMap<>();

    Task.Generator expressionTaskGenerator = new ExpressionTask.Generator(-10, 10,
        EnumSet.allOf(Operation.class));
    Quiz expressionQuiz = new Quiz(expressionTaskGenerator, 10);
    quizMap.put("Expressions", expressionQuiz);

    Task.Generator equationTaskGenerator = new EquationTask.Generator(-10, 10,
        EnumSet.allOf(Operation.class));
    Quiz equationQuiz = new Quiz(equationTaskGenerator, 10);
    quizMap.put("Equations", equationQuiz);

    Collection<Task> tasks = new ArrayList<>();
    tasks.add(new TextTask("Translate 'apple'", "яблык"));
    tasks.add(new TextTask("Translate 'cat'", "кот"));
    tasks.add(new TextTask("Translate 'paper'", "папера"));

    Task.Generator poolTaskGenerator1 = new PoolTaskGenerator(true,
        new TextTask("Translate 'apple'", "яблык"), new TextTask("Translate 'cat'", "кот"),
        new TextTask("Translate 'paper'", "папера"));
    Quiz poolQuiz1 = new Quiz(poolTaskGenerator1, 10);
    quizMap.put("Pool allowing duplicates", poolQuiz1);

    Task.Generator poolTaskGenerator2 = new PoolTaskGenerator(true, tasks);
    Quiz poolQuiz2 = new Quiz(poolTaskGenerator2, 10);
    quizMap.put("Pool's collection allowing duplicates", poolQuiz2);

    Task.Generator poolTaskGenerator3 = new PoolTaskGenerator(false,
        new TextTask("Translate 'apple'", "яблык"), new TextTask("Translate 'cat'", "кот"),
        new TextTask("Translate 'paper'", "папера"));
    Quiz poolQuiz3 = new Quiz(poolTaskGenerator3, 2);
    quizMap.put("Pool not allowing duplicates", poolQuiz3);

    Task.Generator poolTaskGenerator4 = new PoolTaskGenerator(false, tasks);
    Quiz poolQuiz4 = new Quiz(poolTaskGenerator4, 2);
    quizMap.put("Pool's collection not allowing duplicates", poolQuiz4);

    Collection<Task.Generator> generators1 = new ArrayList<>();
    generators1.add(expressionTaskGenerator);
    generators1.add(equationTaskGenerator);
    generators1.add(poolTaskGenerator3);

    Task.Generator groupTaskGenerator1 = new GroupTaskGenerator(expressionTaskGenerator,
        equationTaskGenerator, poolTaskGenerator3);
    Quiz groupQuiz1 = new Quiz(groupTaskGenerator1, 10);
    quizMap.put("Group", groupQuiz1);

    Task.Generator groupTaskGenerator2 = new GroupTaskGenerator(generators1);
    Quiz groupQuiz2 = new Quiz(groupTaskGenerator2, 10);
    quizMap.put("Group's collection", groupQuiz2);

    return quizMap;
  }

  public static void main(String[] args) {
    Map<String, Quiz> quizMap = getQuizMap();

    System.out.println("Available tests:");
    for (String quizName : quizMap.keySet()) {
      System.out.println("- " + quizName);
    }
    System.out.println();

    Scanner scanner = new Scanner(System.in);
    String selectedQuizName;
    do {
      System.out.print("Enter the name of the test: ");
      selectedQuizName = scanner.nextLine();
    } while (!quizMap.containsKey(selectedQuizName));

    Quiz selectedQuiz = quizMap.get(selectedQuizName);

    selectedQuiz.updateAvailableIndexes();
    while (!selectedQuiz.isFinished()) {
      Task task = selectedQuiz.nextTask();
      if (task != null) {
        System.out.println("Question: " + task.getText());
        String answer = scanner.nextLine();
        Result result = selectedQuiz.provideAnswer(answer);
        System.out.println("Result: " + result);
        System.out.println();
      }
    }

    int incorrectInputNumber = selectedQuiz.getIncorrectInputNumber();
    int wrongAnswerNumber = selectedQuiz.getWrongAnswerNumber();
    int correctAnswerNumber = selectedQuiz.getCorrectAnswerNumber();
    double mark = selectedQuiz.getMark();
    System.out.println("Number of incorrect inputs: " + incorrectInputNumber);
    System.out.println("Number of wrong answers: " + wrongAnswerNumber);
    System.out.println("Number of correct answers: " + correctAnswerNumber);
    System.out.println("Your mark: " + mark);
  }
}
