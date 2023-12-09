package by.DashaGnedko.quizer;

import by.DashaGnedko.quizer.exceptions.QuizNotFinishedException;
import by.DashaGnedko.quizer.task_generators.GroupTaskGenerator;
import by.DashaGnedko.quizer.task_generators.PoolTaskGenerator;
import by.DashaGnedko.quizer.tasks.Task;
import by.DashaGnedko.quizer.tasks.TextTask;
import by.DashaGnedko.quizer.tasks.math_tasks.EquationMathTask;
import by.DashaGnedko.quizer.tasks.math_tasks.ExpressionMathTask;
import by.DashaGnedko.quizer.tasks.math_tasks.Operation;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Quiz> quiz = getQuizMap();
        System.out.println("Введите название теста...");

        Scanner in = new Scanner(System.in);
        String name;
        name = in.nextLine();
        while (!quiz.containsKey(name)) {
            System.out.println("Введите название теста...");
            name = in.nextLine();
        }

        Quiz test = quiz.get(name);
        while (!test.isFinished()) {
            Task task = test.nextTask();
            System.out.println(task.getText());
            String answer = in.nextLine();
            test.provideAnswer(answer);
            System.out.println("Correct = " + test.getCorrectAnswerNumber());
            System.out.println("Incorrect = " + test.getIncorrectInputNumber());
            System.out.println("Wrong " + test.getWrongAnswerNumber());
        }

        try {
            System.out.println("Correct " + test.getMark() + "%");
        } catch (Exception e) {
        }

        in.close();
    }

    static Map<String, Quiz> getQuizMap() {
        HashMap<String, Quiz> quizMap = new HashMap<>();

        ArrayList<Task> questions = new ArrayList<>(List.of(
                new TextTask("Какая мечта у пределов?", "Предел мечтаний"),
                new TextTask("Собаки или коты?", "Коты"),
                new TextTask("3+5=7 верно?", "Неверно"),
                new TextTask("3+7=?", "10"),
                new TextTask("Люблю C++", "Me too"),
                new TextTask("Хочу спать", "Хочу кушать")
        )
        );
        quizMap.put("Funny questions", new Quiz(new PoolTaskGenerator(false, questions), 6));
        quizMap.put("Funny questions with repeat", new Quiz(new PoolTaskGenerator(true, questions), 5));

        Task.Generator forDouble = new ExpressionMathTask.Generator(-15, 10, 3, EnumSet.allOf(Operation.class));
        Task.Generator forDoubleDivisionOnly = new ExpressionMathTask.Generator(-15, 10, 4, EnumSet.of(Operation.DIVISION));
        quizMap.put("Double 3", new Quiz(forDouble, 7));
        quizMap.put("Double 4 division only", new Quiz(forDouble, 10));

        Task.Generator forInt = new ExpressionMathTask.Generator(-15, 10, 0, EnumSet.allOf(Operation.class));
        Task.Generator forIntDivisionOnly = new ExpressionMathTask.Generator(-15, 10, 0, EnumSet.of(Operation.DIVISION));
        quizMap.put("Int", new Quiz(forInt, 7));
        quizMap.put("Int division only", new Quiz(forIntDivisionOnly, 10));

        Task.Generator forIntEquation = new EquationMathTask.Generator(-15, 10, 0, EnumSet.allOf(Operation.class));
        Task.Generator forIntDivisionOnlyEquation = new EquationMathTask.Generator(-15, 10, 0, EnumSet.of(Operation.DIVISION));
        quizMap.put("Int for equation", new Quiz(forIntEquation, 7));
        quizMap.put("Int division only for equation", new Quiz(forIntDivisionOnlyEquation, 10));

        quizMap.put("All", new Quiz(new GroupTaskGenerator(forDouble, forInt), 10));

        return quizMap;
    }
}
