package by.Lenson423.quizer;

import by.Lenson423.quizer.exceptions.CantGenerateTask;
import by.Lenson423.quizer.exceptions.QuizNotFinishedException;
import by.Lenson423.quizer.task_generators.GroupTaskGenerator;
import by.Lenson423.quizer.task_generators.PoolTaskGenerator;
import by.Lenson423.quizer.tasks.TextTask;
import by.Lenson423.quizer.tasks.math_tasks.AbstractMathTask;
import by.Lenson423.quizer.tasks.math_tasks.EquationTask;
import by.Lenson423.quizer.tasks.math_tasks.ExpressionTask;
import by.Lenson423.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class Main {
    public static void main(String[] args) throws QuizNotFinishedException, CantGenerateTask {
        Scanner in = new Scanner(System.in);
        var tmp = getQuizMap();
        var quiz = tmp.get("A");
        checkQuiz(in, quiz);

        quiz = tmp.get("B");
        checkQuiz(in, quiz);

        quiz = tmp.get("C");
        try {
            checkQuiz(in, quiz);
            assert true;
        } catch (IllegalArgumentException ignored) {
        }

        quiz = tmp.get("D");
        checkQuiz(in, quiz);

        quiz = tmp.get("E");
        try {
            checkQuiz(in, quiz);
            assert true;
        } catch (CantGenerateTask ignored) {
        }
        System.out.println("Checks are passed");
    }

    private static void checkQuiz(Scanner in, Quiz quiz) {
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            Result res = quiz.provideAnswer(in.nextLine());
            //Result res = quiz.provideAnswer("1");
            System.out.println(res);
        }
        System.out.println("Incorrect input: " + quiz.getIncorrectInputNumber());
        System.out.println("Mark: " + quiz.getMark());
        System.out.println("Correct answers: " + quiz.getCorrectAnswerNumber());
        System.out.println("Wrong answers: " + quiz.getWrongAnswerNumber());
    }

    /**
     * @return тесты в {@link Map}, где
     * ключ     - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        List<Task.Generator> list = new LinkedList<>();
        list.add(new EquationTask.Generator(-15, -1,
                EnumSet.of(MathTask.Operation.MULTIPLICATION, MathTask.Operation.DIVISION, MathTask.Operation.SUM),
                3));
        list.add(new ExpressionTask.Generator(20, 29,
                EnumSet.of(MathTask.Operation.DIVISION, MathTask.Operation.SUM), 4));
        Quiz firstQuiz = new Quiz(new GroupTaskGenerator(
                list),
                100000);
        Map<String, Quiz> map = new HashMap<>();
        map.put("A", firstQuiz);

        TextTask task1 = new TextTask("Best soviet movie is ...", "The Youth of Maxim");
        AbstractMathTask task2 = new EquationTask(5, MathTask.Operation.DIFFERENCE, 22, 0);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        PoolTaskGenerator poolTaskGenerator = new PoolTaskGenerator(true, tasks);
        Quiz secondQuiz = new Quiz(poolTaskGenerator, 4);
        map.put("B", secondQuiz);

        GroupTaskGenerator tasks3 = new GroupTaskGenerator(
                new ExpressionTask.Generator(0, 0, EnumSet.of(MathTask.Operation.DIVISION))
        );
        Quiz thirdQuiz = new Quiz(tasks3, 1);
        map.put("C", thirdQuiz); //Should be exception


        GroupTaskGenerator tasks4 = new GroupTaskGenerator(
                new EquationTask.Generator(0, 0, EnumSet.of(MathTask.Operation.DIVISION), 2),
                new ExpressionTask.Generator(-3, 3, EnumSet.of(MathTask.Operation.SUM), 2)
        );
        Quiz fourthQuiz = new Quiz(tasks4, 3);
        map.put("D", fourthQuiz); //Should not be exception

        TextTask task5 = new TextTask("Best soviet movie is ...", "The Youth of Maxim");
        List<Task> tasks5 = new ArrayList<>();
        tasks5.add(task5);
        PoolTaskGenerator poolTaskGenerator5 = new PoolTaskGenerator(false, tasks5);
        Quiz fifthQuiz = new Quiz(poolTaskGenerator5, 4);
        map.put("E", fifthQuiz); //should generate exception

        return map;
    }
}
