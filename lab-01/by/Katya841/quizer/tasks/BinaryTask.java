package by.Katya841.quizer.tasks;

import by.Katya841.quizer.Result;

public class BinaryTask implements Task {
    private final int value;
    private int cnt;
    void countNumberOfBits() {
        for (int i = 0; (1 << i) < value; i++) {
            if ( (value & (1 << i)) > 0) {
                cnt++;
            }
        }
    }
    public BinaryTask(int value) {
        this.value = value;
        countNumberOfBits();
    }

    @Override
    public String getText() {
        return "Count total bit in " + value;
    }
    @Override
    public Result validate(String answer) {
        int number = Integer.parseInt(answer);
        if (number == cnt) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
}
