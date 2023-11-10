package by.Roman191976.Quizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        int i = 1;
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
        quizMap.put("Quiz 1", new Quiz(new ExpressionTaskGenerator(-5, 20, false, false, false, true), 2));       
        return quizMap;
    }
}
