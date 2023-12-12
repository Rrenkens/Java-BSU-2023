package by.busskov.quizer.exceptions;

public class NoAvailableTasksException extends GenerateException {
    public NoAvailableTasksException() {
        super();
    }

    public NoAvailableTasksException(String message) {
        super(message);
    }

    public NoAvailableTasksException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableTasksException(Throwable cause) {
        super(cause);
    }

    protected NoAvailableTasksException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
