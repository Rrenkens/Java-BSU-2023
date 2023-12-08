package by.mnik0_0.quizer;

import by.mnik0_0.quizer.Result;

public interface Task {
    public interface Generator {
        Task generate();
    }

    String getText();

    Result validate(String answer);
}