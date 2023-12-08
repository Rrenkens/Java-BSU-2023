package by.rycbaryana.quizer;

public class Answer {
    private final String textAnswer;
    private double numAnswer;
    private boolean isNumeric = false;

    public Answer(String answer) {
        textAnswer = answer;
        try {
            numAnswer = Double.parseDouble(answer);
            isNumeric = true;
        } catch (NumberFormatException ignored) {
        }
    }

    public double getNum() {
        return numAnswer;
    }

    public String getText() {
        return textAnswer;
    }

    public boolean isNumeric() {
        return isNumeric;
    }
}
