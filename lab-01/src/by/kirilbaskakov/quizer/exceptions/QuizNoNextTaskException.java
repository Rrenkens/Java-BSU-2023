package by.kirilbaskakov.quizer.exceptions;

public class QuizNoNextTaskException extends RuntimeException {
    public QuizNoNextTaskException(String text) {
        super(text);
    }
}
