package by.mnik0_0.quizer;

import by.mnik0_0.quizer.exceptions.NoTasksException;
import by.mnik0_0.quizer.exceptions.QuizNotFinishedException;
import by.mnik0_0.quizer.tasks.math_tasks.EquationMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.ExpressionMathTask;
import by.mnik0_0.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> map = new HashMap<>();

        EnumSet<MathTask.Operation> allOperations = EnumSet.allOf(MathTask.Operation.class);
        EnumSet<MathTask.Operation> sumAndDifferenceOperations = EnumSet.of(MathTask.Operation.Sum, MathTask.Operation.Difference);
        EnumSet<MathTask.Operation> multiplicationAndDivisionOperations = EnumSet.of(MathTask.Operation.Multiplication, MathTask.Operation.Division);

        EquationMathTask.Generator equationMathTask1 = new EquationMathTask.Generator(-100, 100, allOperations, 3);
        EquationMathTask.Generator equationMathTask2 = new EquationMathTask.Generator(-100, 100, sumAndDifferenceOperations, 2);
        EquationMathTask.Generator equationMathTask3 = new EquationMathTask.Generator(-100, 100, multiplicationAndDivisionOperations, 1);

        ExpressionMathTask.Generator expressionMathTask1 = new ExpressionMathTask.Generator(-100, 100, allOperations, 1);
        ExpressionMathTask.Generator expressionMathTask2 = new ExpressionMathTask.Generator(-100, 100, sumAndDifferenceOperations, 2);
        ExpressionMathTask.Generator expressionMathTask3 = new ExpressionMathTask.Generator(-100, 100, multiplicationAndDivisionOperations, 3);

        map.put("eq all p3 5", new Quiz(equationMathTask1, 5));
        map.put("eq sd p2 5", new Quiz(equationMathTask2, 5));
        map.put("eq md p1 5", new Quiz(equationMathTask3, 5));

        map.put("ex all p1 5", new Quiz(expressionMathTask1, 5));
        map.put("ex sd p2 5", new Quiz(expressionMathTask2, 5));
        map.put("ex md p3 5", new Quiz(expressionMathTask3, 5));

        return map;
    }

    public static void main(String[] args) throws QuizNotFinishedException, NoTasksException {
        Map<String, Quiz> quizMap = getQuizMap();
        Scanner scanner = new Scanner(System.in);

        for (String name:
             quizMap.keySet()) {
            System.out.println(name);
        }

        System.out.println("Enter name of text");
        var quiz_name = scanner.nextLine();
        if (quizMap == null) {
            throw new NoTasksException();
        }
        while (!quizMap.containsKey(quiz_name)) {
            System.out.println("No test with this name");
            quiz_name = scanner.nextLine();
        }
        var quiz = quizMap.get(quiz_name);

        while (!quiz.isFinished()) {
            System.out.println("Enter your answer");
            System.out.println(quiz.nextTask().getText());
            var result = quiz.provideAnswer(scanner.nextLine());
            switch (result) {
                case OK:
                    System.out.println("Correct");
                    break;
                case WRONG:
                    System.out.println("Wrong");
                    break;
                case INCORRECT_INPUT:
                    System.out.println("Incorrect");
                    break;
            }
        }
        System.out.println("Your mark: " + quiz.getMark() + ".");
    }
}
