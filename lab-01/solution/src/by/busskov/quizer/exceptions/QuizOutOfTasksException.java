package by.busskov.quizer.exceptions;

public class QuizOutOfTasksException extends RuntimeException {
    public QuizOutOfTasksException() {
        super();
    }

    public QuizOutOfTasksException(String message) {
        super(message);
    }

    public QuizOutOfTasksException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizOutOfTasksException(Throwable cause) {
        super(cause);
    }

    protected QuizOutOfTasksException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
