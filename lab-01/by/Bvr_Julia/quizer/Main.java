package by.Bvr_Julia.quizer;
import by.Bvr_Julia.quizer.task_generators.GroupTaskGenerator;
import by.Bvr_Julia.quizer.task_generators.PoolTaskGenerator;
import by.Bvr_Julia.quizer.tasks.EquationMathTask;
import by.Bvr_Julia.quizer.tasks.ExpressionMathTask;
import by.Bvr_Julia.quizer.tasks.MathTask;
import by.Bvr_Julia.quizer.tasks.TextTask;

import java. util.*;

import static by.Bvr_Julia.quizer.Result.INCORRECT_INPUT;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = new HashMap<>(getQuizMap());
        System.out.println("Введите название теста...");
        Scanner in = new Scanner(System.in);
        StringBuilder name = new StringBuilder(in.nextLine());
        while (!quizMap.containsKey(name.toString())) {
            System.out.println("Введите правильное название теста...");
            name.delete(0, name.length());
            name.append(in.nextLine());
        }
        Quiz quiz = quizMap.get(name.toString());
        int i = 0;
        while (i < quiz.taskCount) {
            System.out.println(quiz.nextTask().getText());
            Result res = quiz.provideAnswer(in.nextLine()); //!!!
            System.out.println(res.toString());
            if (res != INCORRECT_INPUT) {
                i++;
            }
        }
        System.out.println(quiz.getMark());
    }

    /**
     * @return tests in {@link Map}, where
     * key - test name {@link String}
     * value - the test itself {@link Quiz}
     */
    private static Map<String, Quiz> getQuizMap() {
        ExpressionMathTask.Generator taskGenerator1 = new ExpressionMathTask.Generator(1, 20, EnumSet.of(MathTask.Operation.Multiplication, MathTask.Operation.Difference));
        Quiz quiz1 = new Quiz(taskGenerator1, 10);

        EquationMathTask.Generator taskGenerator2 = new EquationMathTask.Generator(0, 7, EnumSet.of(MathTask.Operation.Sum, MathTask.Operation.Division));
        Quiz quiz2 = new Quiz(taskGenerator2, 10);

        GroupTaskGenerator taskGenerator3 = new GroupTaskGenerator(taskGenerator1,taskGenerator2);
        Quiz quiz3 = new Quiz(taskGenerator3, 10);

        PoolTaskGenerator taskGenerator4 = new PoolTaskGenerator(false, taskGenerator1.generate(),
                taskGenerator2.generate(), taskGenerator1.generate(), taskGenerator2.generate());
        Quiz quiz4 = new Quiz(taskGenerator4, 2);

        PoolTaskGenerator taskGenerator5 = new PoolTaskGenerator(false,
                new TextTask("Capital of Belarus","Minsk"),
                new TextTask("Capital of the UK","London"),new TextTask("Capital of the USA","Washington"),
                new TextTask("Capital of the Germany","Berlin"));
        Quiz quiz5 = new Quiz(taskGenerator5, 4);

        Map<String, Quiz> quizMap = new HashMap<>();
        quizMap.put("EXPRESSIONS", quiz1);
        quizMap.put("EQUATIONS", quiz2);
        quizMap.put("EXPRESSIONS_EQUATIONS", quiz3);
        quizMap.put("FIXED_EXPRESSIONS_EQUATIONS", quiz4);
        quizMap.put("CAPITALS", quiz5);
        return quizMap;
    }
}
