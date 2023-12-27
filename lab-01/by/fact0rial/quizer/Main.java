package by.fact0rial.quizer;

import by.fact0rial.quizer.exceptions.QuizNotFinishedException;
import by.fact0rial.quizer.task_generators.GroupTaskGenerator;
import by.fact0rial.quizer.task_generators.PoolTaskGenerator;
import by.fact0rial.quizer.tasks.TextTask;
import by.fact0rial.quizer.tasks.math_tasks.EquationTask;
import by.fact0rial.quizer.tasks.math_tasks.ExpressionTask;
import by.fact0rial.quizer.tasks.math_tasks.MathTask;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> m = getQuizMap();
        boolean contains = false;
        Scanner read = new Scanner(System.in);
        int i = 1;
        for (String name : m.keySet()) {
            System.out.println(i + ". " + name);
            i++;
        }
        String name = "";
        while (!contains) {
            System.out.println("Введите название теста...");
            name = read.nextLine();
            contains = m.containsKey(name);
            if (!contains) {
                System.out.println("Тест не найден. Попробуйте снова");
            }
        }
        Quiz quiz = m.get(name);
        while (!quiz.isFinished()) {
            try {
                Task t = quiz.nextTask();
                System.out.println(t.getText());
            } catch(Exception ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
            String ans = read.nextLine();
            System.out.println(quiz.provideAnswer(ans));
        }
        try {
            System.out.println("Your mark is " + quiz.getMark());
        } catch (QuizNotFinishedException ex) {
             System.err.println(ex.getMessage());
             System.exit(1);
        }
        read.close();
    }
    static Map<String, Quiz> getQuizMap() {
        var map = new HashMap<String, Quiz>();
        try {
            var count = new ExpressionTask.Generator(0, 10, EnumSet.allOf(MathTask.Operation.class), 3);
            var addition = new EquationTask.Generator(0, 10, EnumSet.allOf(MathTask.Operation.class), 2);
            var history = new PoolTaskGenerator(false, new TextTask("Фамилия 44 президента США", "Обама"), new TextTask("Особо опасная инфекционная болезнь сельскохозяйственных и диких животных всех видов, а также человека.", "сибирская язва"), new TextTask("В каком городе стоит памятник огурцу", "Шклов"), new TextTask("Известный американский математик и террорист", "Тед Качинский"), new TextTask("Как расшифровывается первая буква P в аббревиатуре PIP", "PIP"));
            var group = new GroupTaskGenerator(history, addition);
            var everything = new GroupTaskGenerator(history, addition, count);
            map.put("ПОСЧИТАТЬ", new Quiz(count, 4));
            map.put("ДВА В ОДНОМ", new Quiz(group, 5));
            map.put("ОЧЕНЬ СЛОЖНЫЙ ТЕСТ", new Quiz(addition, 3));
            map.put("КТО ХОЧЕТ СТАТЬ МИЛЛИОНЕРОМ", new Quiz(history, 5));
            map.put("ИТОГОВАЯ КОНТРОЛЬНАЯ РАБОТА", new Quiz(everything, 10));
        } catch(IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        return map;
    }
}
