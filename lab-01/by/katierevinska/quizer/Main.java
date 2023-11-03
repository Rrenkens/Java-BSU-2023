package by.katierevinska.quizer;
import by.katierevinska.quizer.tasks.math_tasks.EquationTask;
import by.katierevinska.quizer.tasks.math_tasks.ExpressionTask;
import by.katierevinska.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * @return ����� � {@link Map}, ���
     * ����     - �������� ����� {@link String}
     * �������� - ��� ����       {@link Quiz}
     */
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        EnumSet<MathTask.Operation> operations = EnumSet.allOf(MathTask.Operation.class);
        try {
            quizMap.put ("First Math test", new Quiz(new ExpressionTask.Generator(0, 10,
                    operations), 5));
            quizMap.put ("Second Math test", new Quiz(new ExpressionTask.Generator(-10, 10,
                    operations), 5));
            quizMap.put ("Optional Math test", new Quiz(new ExpressionTask.Generator(-5, 10,
                    operations), 5));
            quizMap.put ("Level 2 First Math test", new Quiz(new EquationTask.Generator(0, 15,//TODO another equal for double
                    operations), 10));
            quizMap.put ("Level 2 Fast Math test", new Quiz(new EquationTask.Generator(-5, 10,
                    operations), 1));
            quizMap.put ("Level 2 Optional Math test", new Quiz(new EquationTask.Generator(-7, 7,
                    operations), 0));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return quizMap;
    }

    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("������� �������� �����...");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        while(!quizMap.containsKey(name)){
            System.out.println("��������� �������, ���� � ��������� ��������� �� ������...");
            name = scanner.nextLine();
        }
        Quiz quiz = quizMap.get(name);
        while(!quiz.isFinished()){
            System.out.println(quiz.nextTask().getText());
            System.out.println("������� �����(c ��������� �� ������ ����� ����� ������� ��� �������������)...");
            Result result = quiz.provideAnswer(scanner.nextLine());
            if(result == Result.INCORRECT_INPUT) {
                System.out.println("������������ ����, ���������� ��� ���...");
            }else if(result == Result.WRONG){
                System.out.println("�������, ������ ������������");
            }else if(result == Result.OK){
                System.out.println("�������");
            }
        }
        System.out.println("���� �������, ���� ������� " + quiz.getMark());
    }


}
