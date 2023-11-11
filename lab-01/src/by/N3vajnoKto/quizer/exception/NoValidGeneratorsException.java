package by.N3vajnoKto.quizer.exception;

public class NoValidGeneratorsException extends Exception{
    public NoValidGeneratorsException() {
        super("ERROR! No valid generators are available!");
    }
    public NoValidGeneratorsException(String errorMessage) {
        super(errorMessage);
    }
}
