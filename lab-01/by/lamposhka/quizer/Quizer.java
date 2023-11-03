package by.lamposhka.quizer;

import by.lamposhka.quizer.task_generators.EquationTaskGenerator;
import by.lamposhka.quizer.task_generators.ExpressionTaskGenerator;
import by.lamposhka.quizer.tasks.ExpressionTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Quizer {

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        quizMap.put("FUNNY TEST FOR STUPID PEOPLE", new Quiz(new EquationTaskGenerator(
                1,
                10,
                true,
                true,
                true,
                true), 10));
        quizMap.put("300 BUCKS TEST", new Quiz(new ExpressionTaskGenerator(
                3,
                20,
                true,
                true,
                true,
                true), 4));
        return quizMap;
    }

    public static void main(String[] args) {
        System.out.println("Введите название теста...");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String quizName;
        Quiz quiz;
        while (true) {
            try {
                quizName = reader.readLine();
                quiz = getQuizMap().get(quizName);
                if (quiz == null) {
                    throw new IllegalArgumentException(); // ???
                }
                break;
            } catch (IOException e) {
                System.out.println("Input error occurred. Try again.");
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong quiz name. Try again.");
            } catch (Exception e) {
                System.out.println("Unexpected error occurred. Try again.");
            }
        }
        String userAnswer = "";
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());
            try {
                userAnswer = reader.readLine();
            } catch (Exception e) {
                System.out.println("Input error occurred.");
            }
            Result result = quiz.provideAnswer(userAnswer);
            if (result == Result.INCORRECT_INPUT) {
                System.out.println("Incorrect input");
            } else {
                System.out.println(result.toString());
            }
        }
        System.out.println("Your mark: " + quiz.getMark() + "%");
    }
}