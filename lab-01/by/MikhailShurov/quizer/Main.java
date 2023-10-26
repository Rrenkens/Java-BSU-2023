package by.MikhailShurov.quizer;
import java.util.Map;
import java.util.HashMap;
import by.MikhailShurov.quizer.task_generators.EquationTaskGenerator;
import by.MikhailShurov.quizer.task_generators.ExpressionTaskGenerator;
public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();

        //ToDo add some tests
        EquationTaskGenerator equationgGnerator = new EquationTaskGenerator(-100, 100, false, false, true, true);
        ExpressionTaskGenerator exprressionGenerator = new ExpressionTaskGenerator(-100, 100, false, false, true, true);

        Task task1 = exprressionGenerator.generate();
        System.out.println(task1.getText());
        return quizMap;
    }
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
    }
}
