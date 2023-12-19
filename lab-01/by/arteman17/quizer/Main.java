package by.arteman17.quizer;

import by.arteman17.quizer.task_generators.GroupTaskGenerator;
import by.arteman17.quizer.tasks.TextTask;
import by.arteman17.quizer.task_generators.math_task_generators.EquationTaskGenerator;
import by.arteman17.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.arteman17.quizer.task_generators.PoolTaskGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        ExpressionTaskGenerator generator1 = new ExpressionTaskGenerator(5, 20, true, true, true, true);
        EquationTaskGenerator generator2 = new EquationTaskGenerator(2, 6, true, true, true, true);
        GroupTaskGenerator generator3 = new GroupTaskGenerator(generator1, generator2);
        PoolTaskGenerator generator4 = new PoolTaskGenerator(true, generator1.generate(), generator1.generate(), generator2.generate(), generator2.generate());
        PoolTaskGenerator generator5 = new PoolTaskGenerator(false, generator1.generate(), generator1.generate(), generator2.generate(), generator2.generate());
        PoolTaskGenerator generator6 = new PoolTaskGenerator(false,
                new TextTask("Кто является бронированным титаном?", "Райнер Браун"),
                new TextTask("Сколько всего есть стен?", "3"),
                new TextTask("Как зовут главного героя?", "Эрен Егер"),
                new TextTask("Как называется вид войск, который следит за стенами?", "Гарнизон"),
                new TextTask("Какие войска выбрал Эрен?", "Разведкорпус"));

        Map<String, Quiz> map = new HashMap<>();
        Quiz quiz1 = new Quiz(generator1, 10);
        Quiz quiz2 = new Quiz(generator2, 6);
        Quiz quiz3 = new Quiz(generator3, 8);
        Quiz quiz4 = new Quiz(generator4, 5);
        Quiz quiz5 = new Quiz(generator5, 5);
        Quiz quiz6 = new Quiz(generator6, 5);
        map.put("Expression", quiz1);
        map.put("Equation", quiz2);
        map.put("Group", quiz3);
        map.put("Pool", quiz4);
        map.put("PoolException", quiz5);
        map.put("Text", quiz6);
        return map;
    }

    public static void main(String[] args) {
        Map<String, Quiz> map = getQuizMap();
        System.out.println("Введите название теста: ");
        Scanner in = new Scanner(System.in);
        StringBuilder name = new StringBuilder(in.nextLine());
        while (!map.containsKey(name.toString())) {
            System.out.println("Введите название теста: ");
            name.delete(0, name.length());
            name.append(in.nextLine());
        }

        if (!name.toString().equals("Text")) {
            System.out.println("Дробные числа вводите с 6 знаками после запятой");
        }
        Quiz quiz = map.get(name.toString());

        for (int i = 0; i < quiz.taskCount_; ++i) {
            System.out.println(quiz.nextTask().getText());
            Result answer = quiz.provideAnswer(in.nextLine());
            System.out.println(answer.toString());
            if (answer == Result.INCORRECT_INPUT) {
                --i;
            }
        }

        System.out.println(quiz.getMark());
    }
}
