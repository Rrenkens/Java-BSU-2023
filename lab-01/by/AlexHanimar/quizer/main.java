package by.AlexHanimar.quizer;

import by.AlexHanimar.quizer.exceptions.CheckException;
import by.AlexHanimar.quizer.exceptions.NoNextTaskException;
import by.AlexHanimar.quizer.exceptions.QuizNotFinishedException;
import by.AlexHanimar.quizer.task_generators.GroupTaskGenerator;
import by.AlexHanimar.quizer.task_generators.PoolTaskGenerator;
import by.AlexHanimar.quizer.tasks.TextTask;
import by.AlexHanimar.quizer.tasks.math_tasks.EquationTask;
import by.AlexHanimar.quizer.tasks.math_tasks.ExpressionTask;
import by.AlexHanimar.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        var quizes = getQuizMap();
        System.out.print("Введите название теста:");
        for (var x : quizes.keySet())
            System.out.print(" " + x);
        System.out.println();
        var reader = new Scanner(System.in);
        String testName;
        do {
            testName = reader.nextLine();
            if (!quizes.containsKey(testName))
                System.out.println("Введите пожалуйста название теста");
        } while (!quizes.containsKey(testName));
        var quiz = quizes.get(testName);
        while (!quiz.isFinished()) {
            try {
                System.out.println(quiz.nextTask().getText());
                var ans = reader.nextLine();
                quiz.provideAnswer(ans);
            } catch (NoNextTaskException ex) {
                System.out.println("No tasks can be generated(");
                break;
            } catch (CheckException ex) {
                System.out.println("How did you do this?");
            }
        }
        try {
            System.out.printf("Ваша отметка - %f%n", quiz.getMark());
        } catch (QuizNotFinishedException ex) {
            System.out.println("Quiz ended too early");
        }
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        var res = new HashMap<String, Quiz>();
        res.put("equation_quiz", new Quiz(new EquationTask.Generator(0, 10, EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)), 5));
        res.put("expression_quiz", new Quiz(new ExpressionTask.Generator(0, 10, EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)), 5));
        res.put("pool_text_quiz", new Quiz(new PoolTaskGenerator(true, new TextTask("enter 5", "5"), new TextTask("enter 4", "4")), 5));
        res.put("group_test_quiz", new Quiz(new GroupTaskGenerator(
                new EquationTask.Generator(0, 10, EnumSet.of(MathTask.Operation.SUM,
                        MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)),
                new ExpressionTask.Generator(0, 10, 0, EnumSet.of(MathTask.Operation.SUM,
                        MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)),
                new PoolTaskGenerator(true, new TextTask("enter 5", "5"), new TextTask("enter 4", "4"))
        ), 10));
        res.put("very_long_quiz", new Quiz(new EquationTask.Generator(0, 100, 1, EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)), 100));
        res.put("long_group_but_short_gen", new Quiz(new GroupTaskGenerator(
                new PoolTaskGenerator(false, new TextTask("enter 5", "5"))
        ), 100));
        res.put("expression_quiz_precision", new Quiz(new ExpressionTask.Generator(0, 10, 2, EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)), 5));
        res.put("equation_quiz_precision", new Quiz(new EquationTask.Generator(0, 10, 2, EnumSet.of(MathTask.Operation.SUM,
                MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL)), 5));
        return res;
    }
}
