package by.MikhailShurov.quizer;
import java.awt.*;
import java.util.*;

import by.MikhailShurov.quizer.task_generators.EquationTaskGenerator;
import by.MikhailShurov.quizer.task_generators.ExpressionTaskGenerator;
import by.MikhailShurov.quizer.task_generators.PoolTaskGenerator;
import by.MikhailShurov.quizer.task_generators.GroupTaskGenerator;
public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();

        //ToDo add some tests
        EquationTaskGenerator equationgGnerator1 = new EquationTaskGenerator(-100, 100, true, true, true, true);
        EquationTaskGenerator equationgGnerator2 = new EquationTaskGenerator(0, 1000, true, true, true, true);
        EquationTaskGenerator equationgGnerator3 = new EquationTaskGenerator(-1, 1, true, true, true, true);
        EquationTaskGenerator equationgGnerator4 = new EquationTaskGenerator(0, 0, false, false, false, true);
        ExpressionTaskGenerator exprressionGenerator1 = new ExpressionTaskGenerator(-100, 100, true, true, true, true);
        ExpressionTaskGenerator exprressionGenerator2 = new ExpressionTaskGenerator(0, 1000, true, true, true, true);
        ExpressionTaskGenerator exprressionGenerator3 = new ExpressionTaskGenerator(-1, 1, true, true, true, true);
        ExpressionTaskGenerator exprressionGenerator4 = new ExpressionTaskGenerator(0, 0, true, true, true, true);

        GroupTaskGenerator groupTaskGenerator = new GroupTaskGenerator(equationgGnerator1, equationgGnerator2, equationgGnerator3, equationgGnerator4, exprressionGenerator1, exprressionGenerator2, exprressionGenerator3, exprressionGenerator4);

        Set<Task> arrayListWithTasks = new HashSet<>();
        for (int i = 0; i < 1000; ++ i) {
            Task task = groupTaskGenerator.generate();
            arrayListWithTasks.add(task);
        }

        PoolTaskGenerator poolTaskGenerator = new PoolTaskGenerator(false, arrayListWithTasks);

        for (int i = 0; i < 660; ++ i) {
            Task task = poolTaskGenerator.generate();
            Result result = task.validate(task.getAnswer());
            System.out.println(task.getText() + " " + task.getAnswer() + " " + result.toString());
        }
        return quizMap;
    }
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
    }
}
