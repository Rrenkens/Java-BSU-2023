package by.N3vajnoKto.quizer;

import by.N3vajnoKto.quizer.task_generators.CircleGenerator;
import by.N3vajnoKto.quizer.tasks.TextTask;
import by.N3vajnoKto.quizer.tasks.math_tasks.AbstractMathTask;
import by.N3vajnoKto.quizer.tasks.math_tasks.EquationTask;
import by.N3vajnoKto.quizer.tasks.math_tasks.ExpressionTask;
import by.N3vajnoKto.quizer.task_generators.GroupTaskGenerator;
import by.N3vajnoKto.quizer.task_generators.PoolTaskGenerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Quizer {
    public static void main(String[] args) throws Exception {
        var quizes = getQuizMap();
        Quiz quiz;
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Тесты:");
            for (var q : quizes.entrySet()) {
                System.out.println(q.getKey());
            }
            System.out.println("Введите название теста...");

            String name = in.nextLine();

            if (quizes.containsKey(name)) {
                quiz = quizes.get(name);
                break;
            }
        }

        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());

            String res = in.nextLine();

            quiz.provideAnswer(res);
        }

        System.out.println("Правильных ответов: " + quiz.getCorrectAnswerNumber());
        System.out.println("Неправильных ответов: " + quiz.getWrongAnswerNumber());
        System.out.println("Итоговая оценка: " + quiz.getMark());
    }

    static public Map<String, Quiz> getQuizMap() throws Exception {
        Quiz test = new Quiz(new ExpressionTask.Generator(-10, 10, 0, EnumSet.allOf(AbstractMathTask.Operation.class)), 5);
        Quiz fewOperations = new Quiz(new ExpressionTask.Generator(-10, 10, 2, EnumSet.of(AbstractMathTask.Operation.Add, AbstractMathTask.Operation.Sub)), 5);
        Quiz hard = new Quiz(new ExpressionTask.Generator(-50, 50, 2, EnumSet.of(AbstractMathTask.Operation.Mul, AbstractMathTask.Operation.Div)), 5);
        Quiz combined = new Quiz(new GroupTaskGenerator(new ExpressionTask.Generator(-10, 10, 0, EnumSet.of(AbstractMathTask.Operation.Mul, AbstractMathTask.Operation.Div)), new EquationTask.Generator(-10, 10, 2, EnumSet.allOf(AbstractMathTask.Operation.class))), 5);
        Quiz prepared = new Quiz(new PoolTaskGenerator(true, new ExpressionTask("how many 1 in 4", 4, 0), new ExpressionTask("how many 1 in -1", -1, 0)), 5);
        Quiz equations = new Quiz(new EquationTask.Generator(-10, 10, 0, EnumSet.allOf(AbstractMathTask.Operation.class)), 10);
        Quiz div = new Quiz(new EquationTask.Generator(-10, 10, 0, EnumSet.of(AbstractMathTask.Operation.Div)), 10);
        Quiz riddles = new Quiz(new PoolTaskGenerator(true, new TextTask("Самая большая птица", "Андский кондор"), new TextTask("Зимой и летом, одним цветом", "Андский кондор"), new TextTask("Птица, но не летает", "Пингвин или страус")), 3);
        Quiz circles = new Quiz(new CircleGenerator(0, 3, 2), 3);

        var mp = new HashMap<String, Quiz>();

        mp.put("simple", test);
        mp.put("simple2", fewOperations);
        mp.put("hard", hard);
        mp.put("combined", combined);
        mp.put("prepared", prepared);
        mp.put("equations", equations);
        mp.put("division", div);
        mp.put("riddles", riddles);
        mp.put("circles", circles);

        return mp;
    }
}
