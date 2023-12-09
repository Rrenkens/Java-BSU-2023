package by.DashaGnedko.quizer.exceptions;

public class QuizNotFinishedException extends Exception {
    public QuizNotFinishedException() {
    }

    public QuizNotFinishedException(String message) {
        super(message);
    }
}
