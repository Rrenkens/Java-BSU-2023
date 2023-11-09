package by.busskov.quizer.exceptions;

public class InvalidConditionException extends GenerateException {
    public InvalidConditionException() {
        super();
    }

    public InvalidConditionException(String message) {
        super(message);
    }

    public InvalidConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConditionException(Throwable cause) {
        super(cause);
    }

    protected InvalidConditionException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
