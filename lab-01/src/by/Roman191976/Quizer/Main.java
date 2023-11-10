package by.Roman191976.Quizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import by.Roman191976.Quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.Roman191976.Quizer.task_generators.math_task_generators.ExpressionMathTaskGenerator;

public class Main {
       public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Доступные тесты:");
        for (String quizName : quizMap.keySet()) {
            System.out.println("- " + quizName);
        }

        Scanner scanner = new Scanner(System.in);
        String quizName;

        do {
            System.out.print("Введите имя теста: ");
            quizName = scanner.nextLine();
        } while (!quizMap.containsKey(quizName));

        Quiz quiz = quizMap.get(quizName);
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println("Task: " + task.getText());

            System.out.print("Введите ответ: ");
            String answer = scanner.nextLine();

            Result result = quiz.provideAnswer(answer);
            switch (result) {
                case OK:
                    System.out.println("Правильный ответ!");
                    break;
                case WRONG:
                    System.out.println("Непрвильный ответ!");
                    break;
                case INCORRECT_INPUT:
                    System.out.println("Неправильный ввод, ещё раз.");
                    break;
            }
        }
        double mark = quiz.getMark();
        System.out.println("Тест окончен. Оценка: " + mark);
        scanner.close();
    }

/**
 * @return тесты в {@link Map}, где
 * ключ     - название теста {@link String}
 * значение - сам тест       {@link Quiz}
 */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();


    
        List<Task> taskList = new ArrayList<>();

        Task task1 = new TextTask("введи 1", "1");
        Task task2 = new TextTask("введи 2", "2");
        Task task3 = new TextTask("введи 3", "3");

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        TaskGenerator poolTaskGenerator = new PoolTaskGenerator(true, task1, task2, task3, task2, task2);
        Quiz poolQuiz = new Quiz(poolTaskGenerator, 5);
        quizMap.put("Pool Quiz 1", poolQuiz);

        
        TaskGenerator poolTaskGeneratorFromCollection = new PoolTaskGenerator(false, taskList);
        Quiz poolQuizFromCollection = new Quiz(poolTaskGeneratorFromCollection, 3);
        quizMap.put("Pool Quiz From Collection 2", poolQuizFromCollection);


        TaskGenerator equationTaskGenerator = new EquationTaskGenerator(1, 20, true, true, true, true);
        Quiz equationQuiz = new Quiz(equationTaskGenerator, 8);
        quizMap.put("Equation Quiz 3", equationQuiz);

  
        TaskGenerator expressionTaskGenerator = new ExpressionTaskGenerator(1, 20, true, true, true, true);
        Quiz expressionQuiz = new Quiz(expressionTaskGenerator, 6);
        quizMap.put("Expression Quiz 4", expressionQuiz);

         TaskGenerator groupTaskGenerator = new GroupTaskGenerator(equationTaskGenerator, expressionTaskGenerator, poolTaskGenerator);
         Quiz groupQuiz = new Quiz(groupTaskGenerator, 10);
         quizMap.put("Group Quiz 5", groupQuiz);


        TaskGenerator geometryTaskGenerator = new GeometryTaskGenerator(5, 13);
        Quiz geometryQuiz = new Quiz(geometryTaskGenerator, 2);
        quizMap.put("Quiz Geometry 6", geometryQuiz);

        TaskGenerator equationMathTaskGenerator = new EquationMathTaskGenerator(1, 20, true, true, true, true);
        TaskGenerator expressionMathTaskGenerator = new ExpressionMathTaskGenerator(1, 20, true, true, true, true);
        
        TaskGenerator groupMathTaskGenerator = new GroupTaskGenerator(equationMathTaskGenerator, expressionMathTaskGenerator, poolTaskGenerator);
         Quiz groupMathQuiz = new Quiz(groupMathTaskGenerator, 4);
         quizMap.put("Group Math Quiz 7", groupMathQuiz);
        return quizMap;
    }
}
