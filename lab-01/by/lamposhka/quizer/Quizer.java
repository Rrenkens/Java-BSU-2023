package by.lamposhka.quizer;

import by.lamposhka.quizer.task_generators.ExpressionTaskGenerator;
import by.lamposhka.quizer.tasks.ExpressionTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class Quizer {

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        // ...
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Введите название теста...");
        ExpressionTaskGenerator generator = new ExpressionTaskGenerator(0,
                10, false, false, false, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userAnswer = "";
        for (int i = 0; i < 10; ++i) {
            ExpressionTask task = generator.generate();
            System.out.println(task.getText());
            try {
                userAnswer = reader.readLine();
            }  catch (Exception e) {
                System.out.println("exception");
            }

            if (task.validate(userAnswer) == Result.OK) {
                System.out.println("yep");
            }
        }

    }
}
