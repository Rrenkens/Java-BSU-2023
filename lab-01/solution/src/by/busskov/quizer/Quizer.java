package by.busskov.quizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Quizer {
    public static void main(String[] args) {
        Map<String, Quiz> quizes = getQuizMap();
        System.out.println("Введите название теста...");
        Scanner scanner = new Scanner(System.in);
        String quizName;
        while (!quizes.containsKey(quizName = scanner.nextLine())) {}

        Quiz quiz = quizes.get(quizName);
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            String answer = scanner.nextLine();
            quiz.provideAnswer(answer);
        }
        System.out.println("Number of correct answers: " + quiz.getCorrectAnswerNumber());
        System.out.println("Number of wrong answers: " + quiz.getWrongAnswerNumber());
        System.out.println("Number of incorrect inputs: " + quiz.getIncorrectInputAnswerNumber());
        System.out.println("Total mark: " + quiz.getMark());
    }

    public static Map<String, Quiz> getQuizMap() {
        return new HashMap<String, Quiz>();
    }
}
