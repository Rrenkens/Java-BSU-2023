package by.N3vajnoKto.quizer.exception;

public class QuizNotFinishedException extends Exception{
    public QuizNotFinishedException() {
        super("ERROR! Quiz still running!");
    }
    public QuizNotFinishedException(String errorMessage) {
        super(errorMessage);
    }
}
