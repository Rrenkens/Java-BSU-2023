package by.KseniyaGnezdilova.quizer;

import by.KseniyaGnezdilova.quizer.generators.GroupTaskGenerator;
import by.KseniyaGnezdilova.quizer.generators.PoolTaskGenerator;
import by.KseniyaGnezdilova.quizer.tasks.Task;
import by.KseniyaGnezdilova.quizer.tasks.TextTask;
import by.KseniyaGnezdilova.quizer.tasks.math_tasks.EquationTask;
import by.KseniyaGnezdilova.quizer.tasks.math_tasks.ExpressionTask;

import java.util.*;

public class Main {

    static Map<String, Quiz> getQuizMap(){
        Map <String, Quiz> quizzes = new HashMap<>();

        Task.Generator equation = new EquationTask.Generator(0, -20, 20, true, true, true, true);
        Quiz quiz_equation = new Quiz(equation, 10);
        quizzes.put("Equation", quiz_equation);

        Task.Generator expression = new ExpressionTask.Generator(1, -20, 20, true, true, true, true);
        Quiz quiz_expression = new Quiz(expression, 10);
        quizzes.put("Expression", quiz_expression);

        quizzes.put("Notations", new Quiz(new GroupTaskGenerator(equation, expression), 10));

        Vector <Task> textTasks = new Vector<>(List.of(
                new TextTask("Galya has 5 apples, Petya has 12 apples. How many apples does Vanya have, " +
                                    "if it is known that he has 2 times more apples than Galya and Petya combined?", "34"),
                new TextTask("What is the median of a right triangle if its hypotenuse is C?", "C / 2"),
                new TextTask("sin^2(a) + cos^2(a) = ?", "1"),
                new TextTask("The programmer's first word?", "Hello, World!"))
        );

        quizzes.put("Text", new Quiz(new PoolTaskGenerator(false, textTasks), textTasks.size()));

        return quizzes;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map <String, Quiz> quizzes = getQuizMap();
        System.out.println("Available quizzes: ");
        for (Map.Entry<String, Quiz> item: quizzes.entrySet()){
            System.out.println(item.getKey());
        }

        System.out.println("Choose a quiz: ");
        String quizName = scanner.nextLine();
        Quiz quiz = quizzes.get(quizName);
        while (!quiz.isFinished()){
            System.out.println(quiz.nextTask().getText());
            String ans = scanner.nextLine();
            quiz.provideAnswer(ans);
        }
        System.out.println("Result ");
        System.out.println(quiz.getMark());
    }
}