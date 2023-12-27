package by.Dzenia.quizer;
import by.Dzenia.quizer.quiz_exceptions.QuizAnswerAlreadyBeenProvidedException;
import by.Dzenia.quizer.quiz_exceptions.QuizNotFinishedException;
import by.Dzenia.quizer.task_generators.GroupTaskGenerator;
import by.Dzenia.quizer.task_generators.PoolTaskGenerator;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.task_generators.math_task_generators.EquationTaskGenerator;
import by.Dzenia.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.Dzenia.quizer.tasks.Task;
import by.Dzenia.quizer.tasks.TextTask;


import java.io.File;
import java.util.*;

public class Main {
    static Map<String, Quiz> getQuizMap() {
        var allOperations = EnumSet.of(Operation.SUM,
                Operation.DIFFERENCE,
                Operation.DIVISION,
                Operation.MULTIPLICATION);
        HashMap<String, Quiz> quizMap = new HashMap<>();
        var integerExpressionQuiz = new Quiz(new ExpressionTaskGenerator(-100, 100, 0,
                allOperations), 5);
        quizMap.put("Integer Expression Quiz", integerExpressionQuiz);
        var integerEquationQuiz = new Quiz(new EquationTaskGenerator(-10, 20, 0,
                allOperations), 5);
        quizMap.put("Integer Equation Quiz", integerEquationQuiz);
        var groupOfEquationExpressionQuizzes = new Quiz(new GroupTaskGenerator(new EquationTaskGenerator(-10, 20, 1, allOperations),
                new ExpressionTaskGenerator(-10, 20, 1, allOperations)), 6);
        quizMap.put("Expression plus Equation quiz", groupOfEquationExpressionQuizzes);

        var gptTextTasks = new ArrayList<Task>();
        gptTextTasks.add(new TextTask("Что можно увидеть с закрытыми глазами?", "сон"));
        gptTextTasks.add(new TextTask("Что идет, не шевелясь?", "время"));
        gptTextTasks.add(new TextTask("Что следует за вами, но никогда не догоняет вас?", "будущее"));
        gptTextTasks.add(new TextTask("Что можно сломать, называя его?", "молчание"));
        gptTextTasks.add(new TextTask("Что становится длиннее, когда отрезаешь от него часть?", "волосы"));
        var poolGptTextQuiz = new Quiz(new PoolTaskGenerator(false, gptTextTasks), 5);
        quizMap.put("GPT funny questions quiz", poolGptTextQuiz);

        var hardAllQuiz = new Quiz(new GroupTaskGenerator(new GroupTaskGenerator(new EquationTaskGenerator(-100, 100, 2, allOperations),
                new ExpressionTaskGenerator(-100, 100, 2, allOperations)), new PoolTaskGenerator(false, gptTextTasks)), 10);
        quizMap.put("Mixed Quiz", hardAllQuiz);

        try {
            var gener = new EquationTaskGenerator(10, -10, 0, allOperations);
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
        return quizMap;
    }

    static String quizResult(Quiz quiz) throws QuizNotFinishedException {
        if (!quiz.isFinished()) {
            return "Quiz was not finished\n" +
                    "Correct answers: " + quiz.getCorrectAnswerNumber() + '\n' +
                    "Wrong answers: " + quiz.getWrongAnswerNumber() + '\n' +
                    "Incorrect answers: " + quiz.getIncorrectInputNumber();
        }
        return "Quiz was completed\n" +
                "Correct answers: " + quiz.getCorrectAnswerNumber() + '\n' +
                "Wrong answers: " + quiz.getWrongAnswerNumber() + '\n' +
                "Incorrect answers: " + quiz.getIncorrectInputNumber() + '\n' +
                "Mark is " + quiz.getMark();
    }


    public static void main(String[] args) throws CannotGenerateTaskException, QuizAnswerAlreadyBeenProvidedException, QuizNotFinishedException {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Enter the name of the test. Available tests are\n" + quizMap.keySet());
        Scanner reader = new Scanner(System.in);
        String name;
        name = reader.nextLine();
        if (!quizMap.containsKey(name)) {
            System.out.println("There is no such test here");
            return;
        }
        Quiz quiz = quizMap.get(name);
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());
            String answer = reader.nextLine();
            quiz.provideAnswer(answer);
            System.out.println(quizResult(quiz));
        }
        System.out.println(quizResult(quiz));
    }
}
