package by.katierevinska.quizer;

import by.katierevinska.quizer.task_generators.ExpressionTaskGenerator;
import by.katierevinska.quizer.task_generators.EquationTaskGenerator;

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
        try {
            quizMap.put ("First Math test", new Quiz(new ExpressionTaskGenerator(0, 10,
                    true, true, false, false), 5));
            quizMap.put ("Second Math test", new Quiz(new ExpressionTaskGenerator(-10, 10,
                    true, true, false, false), 5));
            quizMap.put ("Optional Math test", new Quiz(new ExpressionTaskGenerator(-5, 10,
                    false, false, true, true), 5));
            quizMap.put ("Level 2 First Math test", new Quiz(new EquationTaskGenerator(0, 15,//TODO another equal for double
                    true, false, true, false), 10));
            quizMap.put ("Level 2 Fast Math test", new Quiz(new EquationTaskGenerator(-5, 10,
                    false, true, false, true), 1));
            quizMap.put ("Level 2 Optional Math test", new Quiz(new EquationTaskGenerator(-7, 7,
                  false, false, false, false), 0));
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
            System.out.println("������� �����...");
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
