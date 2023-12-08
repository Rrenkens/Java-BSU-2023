package by.lamposhka.quizer;

import by.lamposhka.quizer.exceptions.QuizNotFinishedException;
import by.lamposhka.quizer.task_generators.GroupTaskGenerator;
import by.lamposhka.quizer.task_generators.PoolTaskGenerator;
import by.lamposhka.quizer.tasks.math_tasks.*;
import by.lamposhka.quizer.tasks.Task;
import by.lamposhka.quizer.tasks.TextTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Quizer {

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() throws Exception {

        Map<String, Quiz> quizMap = new HashMap<>();
        quizMap.put("FUNNY TEST FOR STUPID PEOPLE", new Quiz(new EquationTask.Generator(
                1,
                10,
                EnumSet.allOf(MathTask.Operation.class)), 10));
        quizMap.put("300 BUCKS TEST", new Quiz(new ExpressionTask.Generator(
                2,
                2,
                EnumSet.allOf(MathTask.Operation.class)), 4));

        quizMap.put("NYATEST", new Quiz(new GroupTaskGenerator(

                new ExpressionTask.Generator(
                        3,
                        20,
                        2,
                        EnumSet.allOf(MathTask.Operation.class)),

                new EquationTask.Generator(
                        1,
                        10,
                        2, EnumSet.allOf(MathTask.Operation.class))), 5));

        quizMap.put("TEST", new Quiz(new PoolTaskGenerator(
                true,
                new TextTask("what?", "yes"),
                new EquationTask("3*3", 9)), 5));

        quizMap.put("OMAEWA", new Quiz(new AppleTask.Generator("Лампошка"), 5));

        return quizMap;
    }

    public static void main(String[] args) {
        System.out.println("Введите название теста...");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String quizName;
        Quiz quiz;
        Map<String, Quiz> map;
        try {
            map = getQuizMap();
        } catch (Exception e) {
            System.out.println("Map initialized with incorrect quiz's.");
            return;
        }
        while (true) {
            try {
                quizName = reader.readLine();
                quiz = map.get(quizName);
                if (quiz == null) {
                    throw new IllegalArgumentException(); // ???
                }
                break;
            } catch (IOException e) {
                System.out.println("Input error occurred. Try again.");
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong quiz name. Try again.");
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                return;
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
            Task.Result result = quiz.provideAnswer(userAnswer);
            if (result == Task.Result.INCORRECT_INPUT) {
                System.out.println("Incorrect input");
            } else {
//                System.out.println(result.toString()); // отладочка
            }
        }
        try {
            System.out.println("Your mark: " + quiz.getMark() + "%");
        } catch (QuizNotFinishedException e) {
            System.out.println("Error occured. Quiz not finished.");
        }

    }
}
