package by.katierevinska.quizer;

import java.util.Map;
import java.util.Scanner;


public class Main {
    private static QuizBuilder quizBuilder = new QuizBuilder();
    public static void main(String[] args) {
        Map<String, Quiz> quizMap = quizBuilder.getQuizMap();
        System.out.println("��������� �������� ������:");
        System.out.println(quizBuilder.getQuizList());
        Scanner scanner = new Scanner(System.in);
        String name = "";
        boolean existingName = false;
        while(!existingName){
            System.out.println("������� �������� �����...");
            name = scanner.nextLine();
            if(!quizMap.containsKey(name)){
                System.out.println("���� � ��������� ��������� �� ������...");
            } else {
                existingName = true;
            }
        }
        Quiz quiz = quizMap.get(name);
        while (!quiz.isFinished()) {
            System.out.println(quiz.nextTask().getText());
            System.out.println("������� ����� c �������� ��������� ���� ����� - �����");
            Result result = quiz.provideAnswer(scanner.nextLine());
            if (result == Result.INCORRECT_INPUT) {
                System.out.println("������������ ����, ���������� ��� ���...");
            } else if (result == Result.WRONG) {
                System.out.println("�������, ������ ������������");
            } else if (result == Result.OK) {
                System.out.println("�������");
            }
        }
        System.out.println("���� �������, ���� ������� " +
                String.format("%.2f", quiz.getMark()) +
                "\n������ �������: " +
                quiz.getCorrectAnswerNumber() +
                "\n���������� �������� �������: " +
                quiz.getWrongAnswerNumber());
    }


}
