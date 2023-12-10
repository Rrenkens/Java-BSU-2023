package by.Katya841.quizer;

import by.Katya841.quizer.exceptions.QuizIsFinishedException;
import by.Katya841.quizer.exceptions.QuizNotFinishedException;
import by.Katya841.quizer.task_generators.GroupTaskGenerator;
import by.Katya841.quizer.task_generators.PoolTaskGenerator;
import by.Katya841.quizer.tasks.BinaryTask;
import by.Katya841.quizer.tasks.Task;
import by.Katya841.quizer.tasks.TextTask;
import by.Katya841.quizer.tasks.math_tasks.EquationMathTask;
import by.Katya841.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        Map<String, Quiz> allQuizes = new HashMap<>();
        allQuizes = getQuizMap();
        System.out.println("Enter the name of the test");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        while(!allQuizes.containsKey(name) || name.isEmpty()) {
            System.out.println("Enter the name of the test");
            name = scanner.nextLine();
        }
        Quiz quiz = allQuizes.get(name);
        quiz.taskCount = 10;
        try {
        while(!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.print(task.getText() + " ");
            String answer = scanner.nextLine();
            System.out.println(quiz.provideAnswer(answer));
           }
            System.out.println("Your mark is " + quiz.getMark());
        } catch (QuizNotFinishedException e) {
            System.out.println("QuizNotFinishedException : " + "It's too early to get mark");
        }
    }
        static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> allQuizes = new HashMap<>();

        // capitals Quiz
        PoolTaskGenerator poolTaskGeneratorCapitals = new PoolTaskGenerator(false,
                new TextTask("The capital of Japan is", "Tokio"),
                new TextTask("The capital of China is", "Beijing"),
                new TextTask("The capital of Russia is", "Moscow"),
                new TextTask("The capital of Italy is", "Rome"),
                new TextTask("The capital of Spain is", "Madrid"));
        Quiz capitalsQuiz = new Quiz(poolTaskGeneratorCapitals, 3);
        allQuizes.put("Capitals", capitalsQuiz);

        // expressions and equations Quiz
        EnumSet<Operation> set1 = EnumSet.of(Operation.Sum, Operation.Difference);
        EnumSet<Operation> set2 = EnumSet.of(Operation.Division, Operation.Multiplication);
        EnumSet<Operation> setAllOperations = EnumSet.of(Operation.Multiplication, Operation.Difference, Operation.Sum, Operation.Division);

        ExpressionMathTask.Generator generatorOfExpressions1 = new ExpressionMathTask.Generator(1, 100, set1);
        ExpressionMathTask.Generator generatorOfExpressions2 = new ExpressionMathTask.Generator(1, 20, set2);
        EquationMathTask.Generator generatorOfEquations1 = new EquationMathTask.Generator(1, 100, set1);
        EquationMathTask.Generator generatorOfEquations2 = new EquationMathTask.Generator(1, 20, set2);


        GroupTaskGenerator groupTaskGeneratorExpressions = new GroupTaskGenerator(generatorOfExpressions1, generatorOfExpressions2);
        GroupTaskGenerator groupTaskGeneratorEquations = new GroupTaskGenerator(generatorOfEquations1, generatorOfEquations2);

        Quiz expressionsQuiz = new Quiz(generatorOfExpressions1, 5);
        Quiz equationsQuiz = new Quiz(generatorOfEquations1, 5);
        Quiz equationsHardQuiz = new Quiz(groupTaskGeneratorEquations, 20);
        Quiz expressionsHardQuiz = new Quiz(groupTaskGeneratorExpressions, 20);

        allQuizes.put("Expressions",  expressionsQuiz);
        allQuizes.put("Equations", equationsQuiz);
        allQuizes.put("EquationsHard", equationsHardQuiz);
        allQuizes.put("ExpressionsHard", expressionsHardQuiz);
        //binary Quiz
         BinaryTask.Generator generatorOfBinaryTasks = new BinaryTask.Generator(1, 256);
         Quiz binaryQuiz = new Quiz(generatorOfBinaryTasks, 10);
         allQuizes.put("Binary", binaryQuiz);

        // common Quiz
        PoolTaskGenerator poolTaskGeneratorCommon = new PoolTaskGenerator(false,
                new TextTask("When the Second World War began, enter the year", "1939"),
                new EquationMathTask(100, 110, Operation.Difference, 2),
                new BinaryTask(127),
                new BinaryTask(4),
                new BinaryTask(54),
                new ExpressionMathTask(10,-10, Operation.Difference)
                );
        Quiz commonQuiz = new Quiz(poolTaskGeneratorCommon, 5);
        allQuizes.put("Common", commonQuiz);
        return allQuizes;
    }
}
