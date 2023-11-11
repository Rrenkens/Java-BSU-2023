package by.nrydo.quizer;

import by.nrydo.quizer.exceptions.NoTasksException;
import by.nrydo.quizer.exceptions.TaskGenerationException;
import by.nrydo.quizer.task_generators.GroupTaskGenerator;
import by.nrydo.quizer.task_generators.PoolTaskGenerator;
import by.nrydo.quizer.task_generators.task_genertors.EquationTaskGenerator;
import by.nrydo.quizer.task_generators.task_genertors.ExpressionTaskGenerator;
import by.nrydo.quizer.tasks.TextTask;
import org.w3c.dom.Text;

import java.util.ArrayList;
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

        var expression = new ExpressionTaskGenerator(1, 10, true, true, true, true);
        var equation = new EquationTaskGenerator(1, 10, true, true, true, true);

        quizzes.put("Арифметика", new Quiz(expression, 5));
        quizzes.put("Выражения", new Quiz(equation, 5));
        quizzes.put("Арифметика и Выражения", new Quiz(new GroupTaskGenerator(expression, equation), 5));

        var task1 = new TextTask("Столица России.", "Москва");
        var task2 = new TextTask("Сколько будет 2+2?", "4");
        var task3 = new TextTask("Самый большой спутник земли.", "Луна");
        var task4 = new TextTask("?", "42");

        quizzes.put("Текстовые вопросы", new Quiz(new PoolTaskGenerator(false, task1, task2, task3, task4), 3));

        return quizzes;
    }

    public static void main(String[] args) {
        var quizzes = getQuizMap();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Доступные тесты:");

        for (var test : quizzes.keySet()) {
            System.out.println(test);
        }

        System.out.println("Введите название теста...");
        var quiz_name = scanner.nextLine();
        while (!quizzes.containsKey(quiz_name)) {
            System.out.println("Такого теста нет, попробуйте ещё раз...");
            quiz_name = scanner.nextLine();
        }
        var quiz = quizzes.get(quiz_name);

        try {
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
            }
            System.out.println("Ваша оценка: " + quiz.getMark() + ".");
        } catch (Exception ex) {
            System.out.println("Ошибка в тесте.");
        }
    }

}
