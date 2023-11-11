package by.nrydo.quizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    /**
     * @return тесты в {@link Map}, где
     * ключ - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        var quizzes = new HashMap<String, Quiz>();

//        quizzes.put("123", new Quiz());

        return quizzes;
    }

    public static void main(String[] args) {
        var quizzes = getQuizMap();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название теста...");
        var quiz_name = scanner.nextLine();
        while (!quizzes.containsKey(quiz_name)) {
            System.out.println("Такого теста нет, попробуйте ещё раз...");
            quiz_name = scanner.nextLine();
        }
        var quiz = quizzes.get(quiz_name);

        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            var result = quiz.provideAnswer(scanner.nextLine());
            switch (result) {
                case OK:
                    System.out.println("Правильный ответ.");
                    break;
                case WRONG:
                    System.out.println("Неправильный ответ.");
                    break;
                case INCORRECT_INPUT:
                    System.out.println("Неправильный формат ответа.");
                    break;
            }
            System.out.println("Ваша оценка: " + quiz.getMark() + ".");
        }
    }

}
