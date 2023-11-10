package tasks;

public class EquationTask  implements Task {
    public String getText() {
    	return task;
    }
    
    
    
    
    public EquationTask (String answer, String taskName) {
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
    	for (int i = 0; i < ans.length(); ++i) {
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
    
    String task;
    String ans;
	@Override
	public boolean equals(Task other) {
		return task.equals(other.getText());
	}
}
