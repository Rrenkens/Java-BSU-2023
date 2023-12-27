package by.Dzenia.quizer;
import by.Dzenia.quizer.quiz_exceptions.QuizAnswerAlreadyBeenProvidedException;
import by.Dzenia.quizer.quiz_exceptions.QuizNotFinishedException;
import by.Dzenia.quizer.task_generators.GroupGenerator;
import by.Dzenia.quizer.task_generators.PoolGenerator;
import by.Dzenia.quizer.task_generators.generator_exceptions.CannotGenerateTaskException;
import by.Dzenia.quizer.tasks.Task;
import by.Dzenia.quizer.tasks.TextTask;
import by.Dzenia.quizer.tasks.math_tasks.EquationTask;
import by.Dzenia.quizer.tasks.math_tasks.ExpressionTask;
import java.util.ArrayList;
import java.util.Map;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Map<String, Quiz> getQuizMap() {
        var allOperations = EnumSet.of(Operation.SUM,
                Operation.DIFFERENCE,
                Operation.DIVISION,
                Operation.MULTIPLICATION);
        HashMap<String, Quiz> quizMap = new HashMap<>();
        var integerExpressionQuiz = new Quiz(new ExpressionTask.Generator(-100, 100, 0,
                allOperations), 5);
        quizMap.put("Integer Expression Quiz", integerExpressionQuiz);
        var integerEquationQuiz = new Quiz(new EquationTask.Generator(-10, 20, 0,
                allOperations), 5);
        quizMap.put("Integer Equation Quiz", integerEquationQuiz);
        var groupOfEquationExpressionQuizzes = new Quiz(new GroupGenerator(new EquationTask.Generator(-10, 20, 1, allOperations),
                new ExpressionTask.Generator(-10, 20, 1, allOperations)), 6);
        quizMap.put("Expression plus Equation quiz", groupOfEquationExpressionQuizzes);

        var gptTextTasks = new ArrayList<Task>();
        gptTextTasks.add(new TextTask("Что можно увидеть с закрытыми глазами?", "сон"));
        gptTextTasks.add(new TextTask("Что идет, не шевелясь?", "время"));
        gptTextTasks.add(new TextTask("Что следует за вами, но никогда не догоняет вас?", "будущее"));
        gptTextTasks.add(new TextTask("Что можно сломать, называя его?", "молчание"));
        gptTextTasks.add(new TextTask("Что становится длиннее, когда отрезаешь от него часть?", "волосы"));
        var poolGptTextQuiz = new Quiz(new PoolGenerator(false, gptTextTasks), 5);
        quizMap.put("GPT funny questions quiz", poolGptTextQuiz);

        try {
            var gener = new EquationTask.Generator(10, -10, 0, allOperations);
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
        var equationDifficultCases = new Quiz(new EquationTask.Generator(0, 1, 0,
                EnumSet.of(Operation.MULTIPLICATION, Operation.DIVISION)), 7);
        quizMap.put("Equation Difficult Cases", equationDifficultCases);
        var hardAllQuiz = new Quiz(new GroupGenerator(new GroupGenerator(new EquationTask.Generator(-100, 100, 2, allOperations),
                new ExpressionTask.Generator(-100, 100, 2, allOperations)), new PoolGenerator(false, gptTextTasks),
                new EquationTask.Generator(0, 1, 0,
                        EnumSet.of(Operation.MULTIPLICATION, Operation.DIVISION))), 10);
        quizMap.put("Mixed Quiz", hardAllQuiz);
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
        String name;
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the name of the test. Available tests are\n" + quizMap.keySet());
            name = reader.nextLine();
            if (!quizMap.containsKey(name)) {
                System.out.println("There is no such test here, try again");
                continue;
            }
            break;
        }
        Quiz quiz = quizMap.get(name);
        while (!quiz.isFinished()) {
            Task task = quiz.nextTask();
            System.out.println(task.getText());
            String answer = reader.nextLine();
            quiz.provideAnswer(answer);
            System.out.println(quizResult(quiz));
        }
    }
}
