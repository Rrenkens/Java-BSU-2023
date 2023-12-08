package by.N3vajnoKto.quizer;

import by.N3vajnoKto.quizer.exception.NoTasksLeftException;
import by.N3vajnoKto.quizer.exception.NoValidGeneratorsException;

public interface Task {
    static public interface Generator {
        Task generate() throws NoValidGeneratorsException, NoTasksLeftException;
    }

    String getText();

    Result validate(String answer);
}