package by.busskov.quizer.exceptions;

public class OutOfTasksException extends RuntimeException {
    public OutOfTasksException() {
        super();
    }

    public OutOfTasksException(String message) {
        super(message);
    }

    public OutOfTasksException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfTasksException(Throwable cause) {
        super(cause);
    }

    protected OutOfTasksException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
