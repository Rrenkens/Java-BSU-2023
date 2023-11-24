package by.aadeglmmy.quizer;

import by.aadeglmmy.quizer.task_generators.EquationTaskGenerator;
import by.aadeglmmy.quizer.task_generators.ExpressionTaskGenerator;
import by.aadeglmmy.quizer.task_generators.GroupTaskGenerator;
import by.aadeglmmy.quizer.task_generators.PoolTaskGenerator;
import by.aadeglmmy.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.aadeglmmy.quizer.task_generators.math_task_generators.ExpressionMathTaskGenerator;
import by.aadeglmmy.quizer.task_generators.math_task_generators.MathTaskGenerator;
import by.aadeglmmy.quizer.tasks.TextTask;
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

    TaskGenerator expressionTaskGenerator = new ExpressionTaskGenerator(-10, 10,
        EnumSet.allOf(Operation.class));
    Quiz expressionQuiz = new Quiz(expressionTaskGenerator, 10);
    quizMap.put("Expressions", expressionQuiz);

    TaskGenerator equationTaskGenerator = new EquationTaskGenerator(-10, 10,
        EnumSet.allOf(Operation.class));
    Quiz equationQuiz = new Quiz(equationTaskGenerator, 10);
    quizMap.put("Equations", equationQuiz);

    Collection<Task> tasks = new ArrayList<>();
    tasks.add(new TextTask("Translate 'apple'", "яблык"));
    tasks.add(new TextTask("Translate 'cat'", "кот"));
    tasks.add(new TextTask("Translate 'paper'", "папера"));

    TaskGenerator poolTaskGenerator1 = new PoolTaskGenerator(true,
        new TextTask("Translate 'apple'", "яблык"), new TextTask("Translate 'cat'", "кот"),
        new TextTask("Translate 'paper'", "папера"));
    Quiz poolQuiz1 = new Quiz(poolTaskGenerator1, 10);
    quizMap.put("Pool allowing duplicates", poolQuiz1);

    TaskGenerator poolTaskGenerator2 = new PoolTaskGenerator(true, tasks);
    Quiz poolQuiz2 = new Quiz(poolTaskGenerator2, 10);
    quizMap.put("Pool's collection allowing duplicates", poolQuiz2);

    TaskGenerator poolTaskGenerator3 = new PoolTaskGenerator(false,
        new TextTask("Translate 'apple'", "яблык"), new TextTask("Translate 'cat'", "кот"),
        new TextTask("Translate 'paper'", "папера"));
    Quiz poolQuiz3 = new Quiz(poolTaskGenerator3, 2);
    quizMap.put("Pool not allowing duplicates", poolQuiz3);

    TaskGenerator poolTaskGenerator4 = new PoolTaskGenerator(false, tasks);
    Quiz poolQuiz4 = new Quiz(poolTaskGenerator4, 2);
    quizMap.put("Pool's collection not allowing duplicates", poolQuiz4);

    Collection<TaskGenerator> generators1 = new ArrayList<>();
    generators1.add(expressionTaskGenerator);
    generators1.add(equationTaskGenerator);
    generators1.add(poolTaskGenerator3);

    TaskGenerator groupTaskGenerator1 = new GroupTaskGenerator(expressionTaskGenerator,
        /*equationTaskGenerator,*/ poolTaskGenerator3);
    Quiz groupQuiz1 = new Quiz(groupTaskGenerator1, 100);
    quizMap.put("Group", groupQuiz1);

    TaskGenerator groupTaskGenerator2 = new GroupTaskGenerator(generators1);
    Quiz groupQuiz2 = new Quiz(groupTaskGenerator2, 10);
    quizMap.put("Group's collection", groupQuiz2);

    MathTaskGenerator expressionMathTaskGenerator = new ExpressionMathTaskGenerator(-10, 10, true,
        true, true, true);
    Quiz expressionMathQuiz = new Quiz(expressionMathTaskGenerator, 10);
    quizMap.put("Abstract expressions", expressionMathQuiz);

    MathTaskGenerator equationMathTaskGenerator = new EquationMathTaskGenerator(-10, 10, true, true,
        true, true);
    Quiz equationMathQuiz = new Quiz(equationMathTaskGenerator, 10);
    quizMap.put("Abstract equations", equationMathQuiz);

    Collection<TaskGenerator> generators2 = new ArrayList<>();
    generators2.add(expressionMathTaskGenerator);
    generators2.add(equationMathTaskGenerator);
    generators2.add(poolTaskGenerator2);

    TaskGenerator groupTaskGenerator3 = new GroupTaskGenerator(expressionMathTaskGenerator,
        equationMathTaskGenerator, poolTaskGenerator2);
    Quiz groupQuiz3 = new Quiz(groupTaskGenerator3, 10);
    quizMap.put("Group with abstract generators", groupQuiz3);

    TaskGenerator groupTaskGenerator4 = new GroupTaskGenerator(generators2);
    Quiz groupQuiz4 = new Quiz(groupTaskGenerator4, 10);
    quizMap.put("Group's collection with abstract generators", groupQuiz4);

    Collection<TaskGenerator> generators3 = new ArrayList<>();
    generators3.add(groupTaskGenerator1);
    generators3.add(groupTaskGenerator4);

    TaskGenerator groupTaskGenerator5 = new GroupTaskGenerator(groupTaskGenerator1,
        groupTaskGenerator4);
    Quiz groupQuiz5 = new Quiz(groupTaskGenerator5, 10);
    quizMap.put("Group with groups", groupQuiz5);

    TaskGenerator groupTaskGenerator6 = new GroupTaskGenerator(generators3);
    Quiz groupQuiz6 = new Quiz(groupTaskGenerator6, 10);
    quizMap.put("Group's collection with groups", groupQuiz6);

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
