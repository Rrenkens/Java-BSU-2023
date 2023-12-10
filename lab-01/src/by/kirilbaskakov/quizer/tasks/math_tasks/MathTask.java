package by.kirilbaskakov.quizer.tasks.math_tasks;

import by.kirilbaskakov.quizer.Task;

public interface MathTask extends Task {
	public enum Operation {
		SUM,
		DIFF,
		MUL,
		DIV
	}
	
	interface Generator extends Task.Generator {
		public double getMinNumber(); // получить минимальное число
		public double getMaxNumber(); // получить максимальное число
		
		default double getDiffNumber() {
			return getMaxNumber() - getMinNumber();
		}
	}
}
