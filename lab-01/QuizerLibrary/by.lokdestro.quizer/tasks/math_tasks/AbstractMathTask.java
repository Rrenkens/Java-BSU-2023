package tasks.math_tasks;

import tasks.Result;
import tasks.Task;

public abstract class AbstractMathTask implements MathTask {
    public String getText() {
    	return task;
    }
    
    
    public AbstractMathTask(String answer, String taskName) {
    	ans = answer;
    	task = taskName;
    }
    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    
    public boolean IsCorrect(String ans) {
    	if (ans.charAt(0) != '-' && !(ans.charAt(0) >= '0' && ans.charAt(0) <= '9')) {
    		return false;
    	}
    	for (int i = 1; i < ans.length(); ++i) {
    		if (!(ans.charAt(i) >= '0' && ans.charAt(i) <= '9')) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public Result validate(String answer) {
    	if (!IsCorrect(answer)) {
    		return Result.INCORRECT_INPUT;
    	}
    	
    	if (ans.equals(answer)) {
    		return Result.OK;
    	} else {
    		return Result.WRONG;
    	}
    }
    
	@Override
	public boolean equals(Task other) {
		return task.equals(other.getText());
	}
    
    String task;
    String ans;

}
