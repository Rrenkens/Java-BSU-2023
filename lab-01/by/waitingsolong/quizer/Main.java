package by.waitingsolong.quizer;

import by.waitingsolong.quizer.task_generators.AppleTaskGenerator;
import by.waitingsolong.quizer.task_generators.GroupTaskGenerator;
import by.waitingsolong.quizer.task_generators.PoolTaskGenerator;
import by.waitingsolong.quizer.tasks.TextTask;
import by.waitingsolong.quizer.tasks.math_tasks.EquationTask;
import by.waitingsolong.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("HELLO");
        Scanner scanner = new Scanner(System.in);

        for (Map.Entry<String, Quiz> entry: getQuizMap().entrySet()) {
            System.out.println("-------------------------");
            System.out.println("Test " + entry.getKey());
            System.out.println("-------------------------");
            Quiz quiz = entry.getValue();
            while (!quiz.isFinished()) {
                Task task = quiz.nextTask();
                System.out.println(task.getText());
                quiz.provideAnswer(scanner.nextLine());
                //System.out.println("Correct answer: " + entry.getValue().getCorrectAnswer());
            }
            System.out.printf("Your mark: %f\nCorrect answers: %d\nWrong answers: %d\nIncorrect input: %d\n", quiz.getMark(), quiz.getCorrectAnswerNumber(), quiz.getWrongAnswerNumber(), quiz.getIncorrectInputNumber());
        }
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        Quiz quiz;
        EnumSet<MathTask.Operation> allOperations = EnumSet.allOf(MathTask.Operation.class);

        // test 1. equations
        Task.Generator g1 = new EquationTask.Generator(1, 10, allOperations, 1);
        quiz = new Quiz(g1, 3);
        quizMap.put("Simple equations", quiz);

        // test 2. expressions
        Task.Generator g2 = new EquationTask.Generator(1, 10, allOperations, 1);
        quiz = new Quiz(g2, 3);
        quizMap.put("Simple expressions", quiz);

        // test 3. apple tasks
        Task.Generator g3 = new AppleTaskGenerator(1, 10);
        quiz = new Quiz(g3, 2);
        quizMap.put("Apple tasks", quiz);

        // test 4. GroupTaskGenerator
        Task.Generator g4 = new GroupTaskGenerator(g1, g2, g3);
        quiz = new Quiz(g4, 4);
        quizMap.put("GroupTaskGenerator", quiz);

        // test 5. PoolTaskGenerator
        Task task1 = new TextTask("какого цвета платье?", "голубое");
        Task task2 = new TextTask("сколько?", "1");
        Task task3 = new TextTask("привет", "ну привет");
        PoolTaskGenerator g5 = new PoolTaskGenerator(false, task1, task2, task3);
        quiz = new Quiz(g5, g5.getPoolSize());
        quizMap.put("PoolTaskGenerator", quiz);

        return quizMap;
    }
}

