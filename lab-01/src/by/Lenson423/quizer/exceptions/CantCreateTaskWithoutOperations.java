package by.Lenson423.quizer.exceptions;

public class CantCreateTaskWithoutOperations extends RuntimeException{
    public CantCreateTaskWithoutOperations(String text) {
        super(text);
    }
}
