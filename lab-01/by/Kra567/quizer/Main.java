package by.Kra567.quizer;
import by.Kra567.quizer.basics.*;
import by.Kra567.quizer.task_generators.GroupTaskGenerator;
import by.Kra567.quizer.task_generators.PoolTaskGenerator;
import by.Kra567.quizer.task_generators.ExpressionTaskGenerator;
import by.Kra567.quizer.task_generators.EquationTaskGenerator;
import by.Kra567.quizer.tasks.TextTask;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
public class Main {

    static Map<String, Quiz> getQuizMap() throws Exception {
        HashMap<String,Quiz> quizzes = new HashMap<>();
        quizzes.put("Test 1",new Quiz(
                new EquationTaskGenerator(-5, 10, true, true, false, false), 3));
        quizzes.put("Test 2",new Quiz(
                new GroupTaskGenerator(
                        new ExpressionTaskGenerator(-20,50,true,false,true,true),
                        new PoolTaskGenerator(false,
                                new TextTask("Столица Зимбабве?","Хараре"),
                                new TextTask("Сколько у кошки жизней(словом)?","девять"),
                                new TextTask("Кто написал произведение \"Алиса в Стране чудес\"(фамилия)","Кэролл")))
                ,9));
        return quizzes;

    }
    static Quiz getQuizFromUser() throws Exception {
        Map<String,Quiz> quizzes = getQuizMap();
        System.out.print("Введите название теста : ");
        Scanner scanner = new Scanner(System.in);
        String quizName = scanner.nextLine();
        while (quizzes.get(quizName) == null){
            System.out.print("Данного теста нет в списке. Попробуйте еще раз.\nВведите название теста : ");
            quizName = scanner.nextLine();
        }
        return quizzes.get(quizName);
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Quiz quizFromUser = getQuizFromUser();
        while (!quizFromUser.isFinished()){
            Task currentTask = quizFromUser.nextTask();
            System.out.println();
            System.out.println("{===== Вопрос N "+quizFromUser.taskNumber() + " =====}");
            System.out.println("Условие : "+currentTask.getText());
            System.out.print("Ответ : ");
            String answer = scanner.nextLine();
            Result res = quizFromUser.provideAnswer(answer);
            switch (res){
                case Result.INCORRECT_INPUT -> System.out.println("Ответ введен неправильно, повторите попытку!");
                case Result.OK -> System.out.println("Молодец!");
                case Result.WRONG -> System.out.println("Неправильно. В следующий раз точно получится!");
            }
        }
        scanner.close();
        System.out.println("\n{===== Результат =====} \n"+
                         "Верно решенных : " + quizFromUser.getCorrectAnswerNumber() + "\n" +
                         "Неверно решенных : " + quizFromUser.getWrongAnswerNumber() + "\n" +
                         "Итого : " + quizFromUser.getMark());
    }
}
