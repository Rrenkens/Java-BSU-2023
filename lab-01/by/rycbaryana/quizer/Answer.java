package by.rycbaryana.quizer;

public class Answer {
    private String textAnswer;
    private double numAnswer;
    private boolean isNumeric = false;

    public Answer(String answer) {
        try {
            numAnswer = Double.parseDouble(answer);
            isNumeric = true;
        } catch (NumberFormatException e) {
            textAnswer = answer;
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
