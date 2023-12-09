package by.DashaGnedko.quizer.tasks;

import by.DashaGnedko.quizer.Result;

public interface Task {
    interface Generator {
        Task generate();
    }
    String getText();

    Result validate(String answer);
}