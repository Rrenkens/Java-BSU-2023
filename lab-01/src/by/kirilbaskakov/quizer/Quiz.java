package by.kirilbaskakov.quizer;

import by.kirilbaskakov.quizer.exceptions.*;

/**
 * Class, который описывает один тест
 */
class Quiz {
	private final Task.Generator generator;
	private final int taskCount;
	
	private Task lastTask = null;
	private int tasks = 0;
	private boolean generateNewTask = true;
	
	private int wrongAnswerNumber = 0;
	private int correctAnswerNumber = 0;
	private int incorrectInputNumber = 0;
		
    /**
     * @param generator генератор заданий
     * @param taskCount количество заданий в тесте
     */
    Quiz(Task.Generator generator, int taskCount) { 
        this.generator = generator;
        this.taskCount = taskCount;
    }
    
    /**
     * @return задание, повторный вызов вернет слелующее
     * @see Task
     */
    Task nextTask() {
    	if (isFinished()) {
    		throw new QuizNoNextTaskException("Error! All tasks have been generated");
    	}
    	if (generateNewTask) {
	        lastTask = generator.generate();
	        tasks++;
	        generateNewTask = false;
    	}
        return lastTask;
    }
    
    /**
     * Предоставить ответ ученика. Если результат {@link Result#INCORRECT_INPUT}, то счетчик неправильных 
     * ответов не увеличивается, а {@link #nextTask()} в следующий раз вернет тот же самый объект {@link Task}.
     */
    Result provideAnswer(String answer) {
        Result result = lastTask.validate(answer);
        switch (result) {
        case INCORRECT_INPUT:
        	generateNewTask = false;
        	incorrectInputNumber++;
        	break;
        case OK:
        	generateNewTask = true;
        	correctAnswerNumber++;
        	break;
        case WRONG:
        	generateNewTask = true;
        	wrongAnswerNumber++;
        }
        return result;
    }
    
    /**
     * @return завершен ли тест
     */
    boolean isFinished() {
        return tasks == taskCount;
    }
    
    /**
     * @return количество правильных ответов
     */
    int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }
    
    /**
     * @return количество неправильных ответов
     */
    int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }
    
    /**
     * @return количество раз, когда был предоставлен неправильный ввод
     */
    int getIncorrectInputNumber() {
    	return incorrectInputNumber;
    }
    
    /**
     * @return оценка, которая является отношением количества правильных ответов к количеству всех вопросов. 
     *         Оценка выставляется только в конце!
     */
    double getMark() {
    	if (!isFinished()) {
    		throw new QuizNotFinishedException("Error! Quiz is not finished");
    	}
        return (double) correctAnswerNumber / taskCount;
    }
}