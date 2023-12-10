package by.Katya841.quizer.tasks;

import by.Katya841.quizer.Result;

public interface Task {
    String getText();
    Result validate(String answer);
    interface Generator {
        Task generate();
    }
}