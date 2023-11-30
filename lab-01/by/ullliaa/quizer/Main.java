package by.ullliaa.quizer.by.ullliaa.quizer;

import by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators.EquationTaskGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.ullliaa.quizer.by.ullliaa.quizer.task_generators.*;
import by.ullliaa.quizer.by.ullliaa.quizer.tasks.TextTask;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Quiz> map = getQuizMap();
        System.out.println("Введите название теста: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        while (!map.containsKey(name)) {
            System.out.println("Введите название теста: ");
            name = in.nextLine();
        }

        System.out.println("Ecли тест является математическим, то вводите числа с 6 знаками после запятой");
        Quiz quiz = map.get(name);

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

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> map = new HashMap<>();
        ExpressionTaskGenerator generator1 = new ExpressionTaskGenerator(-5, -1, true, false, false, true);
        ExpressionTaskGenerator generator2 = new ExpressionTaskGenerator(-1, 6, true, true, true, true);
        EquationTaskGenerator generator3 = new EquationTaskGenerator(0, 10, false, true, true, true);
        PoolTaskGenerator generator4 = new PoolTaskGenerator(false, generator1.generate(), generator1.generate(), generator2.generate(), generator3.generate());
        PoolTaskGenerator generator5 = new PoolTaskGenerator(true, generator2.generate(), generator1.generate());
        PoolTaskGenerator generator6 = new PoolTaskGenerator(false,
                new TextTask("Где ты учишься?","БГУ"),
                new TextTask("Сколько человек у тебя в группе?","26"), new TextTask("Кто проверяет посещаемость пар?","Галя"),
                new TextTask("Ты хочешь отчислиться?","Да"));
        GroupTaskGenerator generator7 = new GroupTaskGenerator(generator1, generator3, generator6);

        Quiz quiz1 = new Quiz(generator1, 10);
        Quiz quiz2 = new Quiz(generator2, 7);
        Quiz quiz3 = new Quiz(generator3, 8);
        Quiz quiz4 = new Quiz(generator4, 2);
        Quiz quiz5 = new Quiz(generator5, 5);
        Quiz quiz6 = new Quiz(generator6, 4);
        Quiz quiz7 = new Quiz(generator7, 20);

        map.put("Negative", quiz1);
        map.put("With 0", quiz2);
        map.put("Help", quiz3);
        map.put("Pool1", quiz4);
        generator4.setTaskCount(2);
        map.put("School", quiz5);
        generator5.setTaskCount(5);
        map.put("University", quiz6);
        generator6.setTaskCount(4);
        map.put("Random", quiz7);
        return map;
    }
}