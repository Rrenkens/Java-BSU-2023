package by.mnik0_0.quizer.exceptions;

public class NoTasksException extends Exception {
    public NoTasksException() {
        super();
    }

    public NoTasksException(String message) {
        super(message);
    }

    public NoTasksException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTasksException(Throwable cause) {
        super(cause);
    }
}
