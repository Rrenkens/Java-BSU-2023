package by.waitingsolong.quizer.task_generators;

import by.waitingsolong.quizer.Quiz;
import by.waitingsolong.quizer.Task;
import by.waitingsolong.quizer.tasks.TextTask;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppleTaskGenerator implements Task.Generator {
    private final int minApples;
    private final int maxApples;

    public AppleTaskGenerator(int minApples, int maxApples) {
        this.minApples = minApples;
        this.maxApples = maxApples;
    }

    @Override
    public Task generate() {
        Random random = new Random();
        int totalApples = random.nextInt(maxApples - minApples + 1) + minApples;
        int givenApples = random.nextInt(totalApples + 1);
        int remainingApples = totalApples - givenApples;

        String question = String.format("У A было %d яблок, он подарил B %d яблок. Сколько яблок осталось у A?", totalApples, givenApples);
        String answer = String.valueOf(remainingApples);

        return new TextTask(question, answer);
    }
}