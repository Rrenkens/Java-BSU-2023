package by.lokdestro.quizer.main;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import by.lokdestro.quizer.Quiz.Quiz;
import by.lokdestro.quizer.task_generators.EquationTaskGenerator;
import by.lokdestro.quizer.task_generators.ExpressionTaskGenerator;
import by.lokdestro.quizer.task_generators.math_task_generators.EquationMathTaskGenerator;
import by.lokdestro.quizer.tasks.ExpressionTask;

public class Main {

    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        quizMap.put ("1expr", new Quiz(new ExpressionTaskGenerator(0, 10,
                true, true, false, false), 5));
        quizMap.put ("2expr", new Quiz(new ExpressionTaskGenerator(-10, 10,
                true, true, false, false), 5));
        quizMap.put ("3expr", new Quiz(new ExpressionTaskGenerator(-5, 10,
                false, false, true, true), 5));
        quizMap.put ("1eq", new Quiz(new EquationTaskGenerator(0, 10,
                true, true, false, false), 5));
        quizMap.put ("2eq", new Quiz(new EquationTaskGenerator(-10, 10,
                true, true, false, false), 5));
        quizMap.put ("3eq", new Quiz(new EquationTaskGenerator(-5, 10,
                false, false, true, true), 5));
        return quizMap;

    }

    public static void main(String[] args) {
        Map<String, Quiz> Quizes = getQuizMap();
        System.out.println("Введите название теста...");
        Scanner console = new Scanner(System.in);
        String name = console.nextLine();
        Quiz curq = Quizes.get(name);
        while (!curq.isFinished()) {
            curq.nextTask();
            String ans = console.nextLine();
            curq.provideAnswer(ans);
        }
        System.out.print(curq.getMark());
    }

}