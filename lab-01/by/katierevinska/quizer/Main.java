package by.katierevinska.quizer;

import java.util.Map;
import java.util.Scanner;


public class Main {
    private static QuizBuilder quizBuilder = new QuizBuilder();
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = quizBuilder.getQuizMap();
        System.out.println("Возможные варианты тестов:");
        System.out.println(quizBuilder.getQuizList());
        Scanner scanner = new Scanner(System.in);
        String name = "";
        boolean existingName = false;
        while(!existingName){
            System.out.println("Введите название теста...");
            name = scanner.nextLine();
            if(!quizMap.containsKey(name)){
                System.out.println("тест с указанным названием не найден...");
            } else {
                existingName = true;
            }
        }
        Quiz quiz = quizMap.get(name);
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            System.out.println("Введите ответ c заданной точностью если ответ - число");
            Result result = quiz.provideAnswer(scanner.nextLine());
            if (result == Result.INCORRECT_INPUT) {
                System.out.println("Некорректный ввод, попробуйте ещё раз...");
            } else if (result == Result.WRONG) {
                System.out.println("Неверно, будьте вниметельнее");
            } else if (result == Result.OK) {
                System.out.println("Молодец");
            }
        }
        System.out.println("Тест окончен, ваша отметка " +
                String.format("%.2f", quiz.getMark()) +
                "\nВерных ответов: " +
                quiz.getCorrectAnswerNumber() +
                "\nКоличество неверных ответов: " +
                quiz.getWrongAnswerNumber());
    }


}
