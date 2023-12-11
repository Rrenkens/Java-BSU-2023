package by.lokdestro.quizer.tasks;

class TextTask implements Task {
    /**
     * @param text   текст задания
     * @param answer ответ на задание
     */
    TextTask(
            String text,
            String answer
    ) {
        this.text = text;
        this.ans = answer;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Task other) {
        return text.equals(other.getText());
    }

    @Override
    public boolean IsCorrect(String ans) {
        for (int i = 0; i < ans.length(); ++i) {
            if (!(ans.charAt(i) >= '0' && ans.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    @Override
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
    public String getAns() {
        return ans;
    }

    String text;
    String ans;
}