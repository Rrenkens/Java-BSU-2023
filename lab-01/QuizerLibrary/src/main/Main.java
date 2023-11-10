package main;
import java.util.Map;
import java.util.Scanner;
import Quiz.Quiz;

public class Main {

	static Map<String, Quiz> getQuizMap() {
	    // ...
	}
	
	public static void main(String[] args) {
		Map<String, Quiz> Quizes = getQuizMap();
		System.out.println("Введите название теста...");
		Scanner console = new Scanner(System.in);
		String name = console.nextLine();
		Quiz curq =new Quizes[name];
		While (!curq.isFinished()) {
			
		}

	}

}
