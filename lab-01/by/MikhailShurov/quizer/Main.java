package by.MikhailShurov.quizer;
import java.awt.*;
import java.util.*;

import by.MikhailShurov.quizer.task_generators.PoolTaskGenerator;
import by.MikhailShurov.quizer.task_generators.GroupTaskGenerator;
import by.MikhailShurov.quizer.tasks.math_tasks.EquationTask;
import by.MikhailShurov.quizer.tasks.math_tasks.ExpressionTask;
import by.MikhailShurov.quizer.tasks.math_tasks.MathTask;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        EnumSet<MathTask.Operation> allOperations = EnumSet.allOf(MathTask.Operation.class);

        //ToDo add some tests
        Quiz quiz;
        EquationTask.Generator equationgGnerator1 = new EquationTask.Generator(-100, 100, allOperations);
        quiz = new Quiz(equationgGnerator1, 10);
        quizMap.put("#1", quiz);

        EquationTask.Generator equationgGnerator2 = new EquationTask.Generator(0, 1000, allOperations);
        quiz = new Quiz(equationgGnerator2, 10);
        quizMap.put("#2", quiz);

        EquationTask.Generator equationgGnerator3 = new EquationTask.Generator(-1, 1, allOperations);
        quiz = new Quiz(equationgGnerator3, 10);
        quizMap.put("#3", quiz);

        EquationTask.Generator equationgGnerator4 = new EquationTask.Generator(0, 0, allOperations);
        quiz = new Quiz(equationgGnerator4, 5);
        quizMap.put("#4", quiz);

//        ExpressionTask.Generator exprressionGenerator1 = new ExpressionTask.Generator(-100, 100, allOperations);
//        quiz = new Quiz(exprressionGenerator1, 10);
//        quizMap.put("Simple equations", quiz);
//
//        ExpressionTask.Generator exprressionGenerator2 = new ExpressionTask.Generator(0, 1000, allOperations);
//        quiz = new Quiz(exprressionGenerator2, 10);
//        quizMap.put("Simple equations", quiz);
//
//        ExpressionTask.Generator exprressionGenerator3 = new ExpressionTask.Generator(-1, 1, allOperations);
//        quiz = new Quiz(exprressionGenerator3, 10);
//        quizMap.put("Simple equations", quiz);
//
//        ExpressionTask.Generator exprressionGenerator4 = new ExpressionTask.Generator(0, 0, allOperations);
//        quiz = new Quiz(exprressionGenerator4, 10);
//        quizMap.put("Simple equations", quiz);

//        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(equationgGnerator1, equationgGnerator2, equationgGnerator3, equationgGnerator4, exprressionGenerator1, exprressionGenerator2, exprressionGenerator3, exprressionGenerator4);
//        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(equationgGnerator1);
//
//        Set<Task> arrayListWithTasks = new HashSet<>();
//        for (int i = 0; i < 11; ++ i) {
//            Task task = groupTaskGenerator.generate();
//            System.out.println(task.getText() + " " + task.getAnswer());
//            arrayListWithTasks.add(task);
//
//        }
//
//        // ToDo why i get similar tasks, but duplicates are not allowed?
//        PoolTaskGenerator poolTaskGenerator = new PoolTaskGenerator(false, arrayListWithTasks);

//        for (int i = 0; i < 660; ++ i) {
//            Task task = poolTaskGenerator.generate();
//            Result result = task.validate(task.getAnswer());
//            System.out.println(task.getText() + " " + task.getAnswer() + " " + result.toString());
//        }

        return quizMap;
    }
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("---------------------------");
        for (Map.Entry<String, Quiz> entry: quizMap.entrySet()) {
            System.out.println("Test: " + entry.getKey());
        }
        System.out.println("---------------------------");



        Scanner scanner = new Scanner(System.in);
        String testName = scanner.nextLine();
        Quiz quiz = quizMap.get(testName);
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());
            String input = scanner.nextLine();
            Result result = quiz.provideAnswer(input);
            System.out.println(result);
        }
        System.out.println(String.valueOf(quiz.getCorrectAnswerNumber()) + " / " + String.valueOf(quiz.getTotalAnswers()));
        System.out.println(String.valueOf(quiz.getMark()));
    }
}
