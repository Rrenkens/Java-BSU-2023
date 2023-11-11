package by.KseniyaGnezdilova.quizer.tasks;

import by.KseniyaGnezdilova.quizer.Result;

public interface Task {
    String getText();

    Result validate(String answer);

    interface Generator {
        Task generate();
    }
}
