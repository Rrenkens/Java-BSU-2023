package by.Lexus_FAMCS.quizer;

import by.Lexus_FAMCS.quizer.task_generators.GroupTaskGenerator;
import by.Lexus_FAMCS.quizer.task_generators.PoolTaskGenerator;
import by.Lexus_FAMCS.quizer.tasks.EquationTask;
import by.Lexus_FAMCS.quizer.tasks.ExpressionTask;
import by.Lexus_FAMCS.quizer.tasks.Task;
import by.Lexus_FAMCS.quizer.tasks.math_tasks.*;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * @return тесты в {@link Map}, где
     * ключ - название теста {@link String}
     * значение - сам тест       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        HashMap<String, Quiz> tests = new HashMap<>();
        tests.put("Expression_5_0-20_all", new Quiz(
                new ExpressionTask.Generator(0,
                        20,
                        EnumSet.of(MathTask.Operation.SUM,
                                MathTask.Operation.SUB,
                                MathTask.Operation.MULT,
                                MathTask.Operation.DIV)), 5));
        tests.put("Expression_4_-10-30_sum_sub", new Quiz(
                new ExpressionTask.Generator(-10,
                        30,
                        EnumSet.of(MathTask.Operation.SUM,
                                MathTask.Operation.SUB)), 4));
        tests.put("Expression_3_-10-30_div", new Quiz(
                new ExpressionTask.Generator(-10,
                        30,
                        EnumSet.of(MathTask.Operation.DIV)), 3));
        tests.put("Equation_5_-10-10_all", new Quiz(
                new EquationTask.Generator(-10,
                        10,
                        EnumSet.of(MathTask.Operation.SUM,
                                MathTask.Operation.SUB,
                                MathTask.Operation.MULT,
                                MathTask.Operation.DIV)), 5));
        tests.put("Equation_4_10-27_mult_div", new Quiz(
                new EquationTask.Generator(10,
                        27,
                        EnumSet.of(MathTask.Operation.MULT,
                                MathTask.Operation.DIV)), 4));
        tests.put("Equation_4_-7-6_div", new Quiz(
                new EquationTask.Generator(-7,
                        6,
                        EnumSet.of(MathTask.Operation.DIV)), 4));
        tests.put("EquationMathTask_5_-1-1_div_0", new Quiz( // генерирует 0.0/x=-1.0
                new EquationMathTask.Generator(-1,
                        1,
                        EnumSet.of(MathTask.Operation.DIV)), 5));
        tests.put("EquationMathTask_5_-5.5-5.5_mult_div_2", new Quiz( // генерирует 0.0/x=-1.0
                new EquationMathTask.Generator(-5.5,
                        5.5,
                        2,
                        EnumSet.of(MathTask.Operation.MULT,
                                MathTask.Operation.DIV)), 5));
        tests.put("EquationMathTask_5_-10-10_sum_sub_3", new Quiz( // генерирует 0.0/x=-1.0
                new EquationMathTask.Generator(-10,
                        10,
                        3,
                        EnumSet.of(MathTask.Operation.SUM,
                                MathTask.Operation.SUB)), 5));
        tests.put("ExpressionMathTask_5_-1-1_div_2", new Quiz( // генерирует 0.0/x=-1.0
                new ExpressionMathTask.Generator(-1,
                        1,
                        2,
                        EnumSet.of(MathTask.Operation.DIV)), 5));
        EquationMathTask.Generator gen1 = new EquationMathTask.Generator(-10,
                10,
                1,
                EnumSet.of(MathTask.Operation.SUM,
                        MathTask.Operation.SUB,
                        MathTask.Operation.MULT,
                        MathTask.Operation.DIV));
        ExpressionMathTask.Generator gen2 = new ExpressionMathTask.Generator(-10,
                10,
                1,
                EnumSet.of(MathTask.Operation.SUM,
                        MathTask.Operation.SUB,
                        MathTask.Operation.MULT,
                        MathTask.Operation.DIV));
        tests.put("ExpressionMathTask_4_-10-10_all_0", new Quiz(gen1, 4));
        tests.put("TestByGroupTaskGenerator_2_-10-10_all_1", new Quiz( // генерирует 0.0/x=-1.0
                new GroupTaskGenerator(gen1, gen2, gen1), 2));
        var task1 = gen1.generate();
        var task2 = gen2.generate();
        tests.put("TestByPoolTaskGenerator_4_-10-10_all_1", new Quiz( // генерирует 0.0/x=-1.0
                new PoolTaskGenerator(true, task1, task2, task1), 6));


        return tests;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Quiz> tests = getQuizMap();
        System.out.println("При получении дробного ответа в не Math тестах округляйте до 3 знаков после запятой");
        System.out.println("В Math тестах нужное кол-во знаков после запятой в ваших ответах регулируется последней цифрой в названии теста");
        String testName, answer;
        Result res;
        Quiz curQuiz;
        Task curTask;
        while (true) {
            System.out.println("Введите название теста: ");
            testName = scanner.nextLine();
            if (!tests.containsKey(testName)) System.err.println("Такого теста не существует!");
            else {
                curQuiz = tests.get(testName);
                if (curQuiz.isFinished()) System.out.println("\033[31mВы уже прошли данный тест!\033[0m");
                while ((curTask = curQuiz.nextTask()) != null) {
                    System.out.println(curTask.getText());
                    answer = scanner.nextLine();
                    res = curQuiz.processAnswer(answer);
                    if (res == Result.OK) System.out.println("\033[32mRight!\033[0m");
                    else if (res == Result.WRONG) System.out.println("\033[31mWrong!\033[0m");
                    else System.out.println("\033[31mIncorrect input!\033[0m");
                }
                System.out.println("--------------------------------------");
                System.out.println("Кол-во вопросов: " + (curQuiz.getCorrectAnswerNumber() + curQuiz.getWrongAnswerNumber()));
                System.out.println("Кол-во правильный ответов: " + curQuiz.getCorrectAnswerNumber());
                System.out.println("Кол-во некорректных ответов: " + curQuiz.getIncorrectInputNumber());
                System.out.println("Оценка: " + curQuiz.getMark());
                System.out.println("--------------------------------------");
            }
            System.out.println("Желаете продолжить?[y(\033[32myes\033[0m)/n(\033[31mno\033[0m)]");
            answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
}
