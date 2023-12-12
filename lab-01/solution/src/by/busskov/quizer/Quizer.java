package by.busskov.quizer;

import by.busskov.quizer.task_generators.GroupTaskGenerator;
import by.busskov.quizer.task_generators.PoolTaskGenerator;
import by.busskov.quizer.tasks.TextTask;
import by.busskov.quizer.tasks.math_tasks.EquationMathTask;
import by.busskov.quizer.tasks.math_tasks.ExpressionMathTask;
import by.busskov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class Quizer {
    public static void main(String[] args) {
        Map<String, Quiz> quizzes = getQuizMap();
        System.out.println("Доступные тесты: ");
        for (String key : quizzes.keySet()) {
            System.out.println(key);
        }
        System.out.println("Введите название теста...");
        Scanner scanner = new Scanner(System.in);
        String quizName;
        while (!quizzes.containsKey(quizName = scanner.nextLine())) {}

        Quiz quiz = quizzes.get(quizName);
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            String answer = scanner.nextLine();
            quiz.provideAnswer(answer);
        }
        System.out.println("Number of correct answers: " + quiz.getCorrectAnswerNumber());
        System.out.println("Number of wrong answers: " + quiz.getWrongAnswerNumber());
        System.out.println("Number of incorrect inputs: " + quiz.getIncorrectInputAnswerNumber());
        System.out.println("Total mark: " + quiz.getMark());
    }

    public static Map<String, Quiz> getQuizMap() {
        ExpressionMathTask.Generator expressionGenerator = new ExpressionMathTask.Generator(
                -5.5,
                5.5,
                EnumSet.allOf(MathTask.Operation.class),
                1
        );

        EquationMathTask.Generator equationGeneratorNotSum;
        try {
            equationGeneratorNotSum = new EquationMathTask.Generator(
                    -0.5,
                    0.5,
                    EnumSet.of(MathTask.Operation.DIVISION),
                    0
            );
        } catch (IllegalArgumentException exception) {
            equationGeneratorNotSum = new EquationMathTask.Generator(
                    -0.5,
                    0.5,
                    EnumSet.of(
                            MathTask.Operation.DIFFERENCE,
                            MathTask.Operation.DIVISION,
                            MathTask.Operation.MULTIPLICATION),
                    1
            );
        }

        EquationMathTask.Generator equationGeneratorSum = new EquationMathTask.Generator(
                -10,
                10,
                EnumSet.of(MathTask.Operation.SUM),
                0
        );

        PoolTaskGenerator poolGeneratorDuplicate = new PoolTaskGenerator(
                true,
                new TextTask("Do you like java?", "Yes"),
                new TextTask("How many fingers should programmer use during typing?", "10"),
                new EquationMathTask("lg(x)=2", 100, 2)
        );

        PoolTaskGenerator poolGeneratorNotDuplicate = new PoolTaskGenerator(
                false,
                new TextTask("Do you like java?", "Yes"),
                new TextTask("How many fingers should programmer use during typing?", "10"),
                new EquationMathTask("lg(x)=2", 100, 2)
        );

        PoolTaskGenerator poolGenerator = new PoolTaskGenerator(
                false,
                new TextTask("Favorite number of programming teachers", "42"),
                new TextTask("How does consciousness emerge from the physical processes of the brain?",
                        "Unknown")
        );

        GroupTaskGenerator groupPoolGenerator = new GroupTaskGenerator(
                poolGenerator,
                poolGeneratorNotDuplicate
        );

        List<EquationMathTask.Generator> list = new ArrayList<>();
        list.add(equationGeneratorSum);
        list.add(equationGeneratorNotSum);
        GroupTaskGenerator groupEquationGenerator = new GroupTaskGenerator(list);

        GroupTaskGenerator groupAllTypesGenerator = new GroupTaskGenerator(
                groupEquationGenerator,
                groupPoolGenerator,
                expressionGenerator
        );

        Map<String, Quiz> map = new HashMap<>();
        map.put("Expressions", new Quiz(expressionGenerator, 2));
        map.put("EquationsNotSum", new Quiz(equationGeneratorNotSum, 10));
        map.put("PoolDuplicate", new Quiz(poolGeneratorDuplicate, 5));
        map.put("PoolNotDuplicate", new Quiz(poolGeneratorNotDuplicate, 3));
        map.put("GroupEquations", new Quiz(groupEquationGenerator, 4));
        map.put("GroupPools", new Quiz(groupPoolGenerator, 5));
        // Cannot generate map.put("GroupPools_Exception", new Quiz(groupPoolGenerator, 6));
        map.put("GroupAllTypes", new Quiz(groupAllTypesGenerator, 5));
        return map;
    }
}
