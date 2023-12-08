package by.N3vajnoKto.quizer.exception;

public class NoTasksLeftException extends Exception{
    public NoTasksLeftException() {
        super("ERROR! No different tests left!");
    }
    public NoTasksLeftException(String errorMessage) {
        super(errorMessage);
    }
}
