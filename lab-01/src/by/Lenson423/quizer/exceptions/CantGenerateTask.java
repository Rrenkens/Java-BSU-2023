package by.Lenson423.quizer.exceptions;

public class CantGenerateTask extends RuntimeException {
    public CantGenerateTask(String text) {
        super(text);
    }

}