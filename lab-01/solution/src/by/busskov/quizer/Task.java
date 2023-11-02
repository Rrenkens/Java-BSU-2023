package by.busskov.quizer;

public interface Task {
    String getText();

    Result validate(String answer);
}
