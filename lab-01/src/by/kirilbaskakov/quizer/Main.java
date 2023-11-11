package by.kirilbaskakov.quizer;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumSet;
import java.util.Scanner;

import by.kirilbaskakov.quizer.tasks.*;
import by.kirilbaskakov.quizer.tasks.math_tasks.*;
import by.kirilbaskakov.quizer.task_generators.*;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Map<String, Quiz> quizes = getQuizMap();
		System.out.println("Enter the name of the quiz");
		String quizName = in.nextLine(); ;
		while (!quizes.containsKey(quizName)) {
			System.out.println("Error! Quiz name is incorrect");
			quizName = in.nextLine(); 
		} 
		System.out.println("Starting the quiz " + quizName);
		Quiz quiz = quizes.get(quizName);
		while (!quiz.isFinished()) {
			Task task = quiz.nextTask();
			System.out.println(task.getText());
			String answer = in.nextLine();
			Result result = quiz.provideAnswer(answer);
			switch (result) {
			case OK:
				System.out.println("Correct!");
				break;
			case WRONG:
				System.out.println("Wrong!");
				break;
			case INCORRECT_INPUT:
				System.out.println("Incorrect input! Try again");
				break;
			}
		}
		System.out.println("Your score is " + Math.round(quiz.getMark() * 100) + "%");
		in.close();
	}
	
	/**
	 * @return тесты в {@link Map}, где
	 * ключ     - название теста {@link String}
	 * значение - сам тест       {@link Quiz}
	 */
	static Map<String, Quiz> getQuizMap() {
		Map<String, Quiz> quizes = new HashMap<String, Quiz>();
		Task.Generator ExpressionGen = new ExpressionMathTask.Generator(1, 10, 2, EnumSet.of(MathTask.Operation.SUM, MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL));
		Task.Generator EquationGen = new EquationMathTask.Generator(1, 8, 1, EnumSet.of(MathTask.Operation.SUM, MathTask.Operation.DIFF, MathTask.Operation.DIV, MathTask.Operation.MUL));
		Task.Generator GroupGen = new GroupTaskGenerator(ExpressionGen, EquationGen);
		Task.Generator PoolGen = new PoolTaskGenerator(true, new ExpressionMathTask(3.0, 7.0, MathTask.Operation.SUM, 10.0, 0), new EquationMathTask(35.0, 7.0, false, MathTask.Operation.DIV, 5.0, 0), new TextTask("2+2=?", "4"));
		quizes.put("Quiz 1", new Quiz(ExpressionGen, 5));
		quizes.put("Quiz 2", new Quiz(EquationGen, 5));
		quizes.put("Quiz 3", new Quiz(GroupGen, 5));
		quizes.put("Quiz 4", new Quiz(PoolGen, 5));
		return quizes;
	}
}
