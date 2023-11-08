package by.Bvr_Julia.quizer.tasks;

import by.Bvr_Julia.quizer.Result;

/**
 * Assignment with pre-prepared text.
 */
public class TextTask implements Task {
    /**
     * @param text task text
     * @param answer the answer to the task
     */
    private final String text;
    private final String answer;
    public TextTask(
            String text,
            String answer
    ) {
        this.text = new String(text);
        this.answer = new String(answer);

    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer.compareTo(this.answer) == 0){
            return Result.OK;
        }else if (!answer.isEmpty()){
            return Result.WRONG;
        }
        return Result.INCORRECT_INPUT;
    }

}