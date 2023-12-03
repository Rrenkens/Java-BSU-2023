package by.waitingsolong.docks_and_hobos.helpers;

public class NameGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateName(int counter) {
        StringBuilder nameBuilder = new StringBuilder();
        do {
            char letter = ALPHABET.charAt((int) (counter % ALPHABET.length()));
            nameBuilder.insert(0, letter);
            counter /= ALPHABET.length();
        } while (counter > 0);
        return nameBuilder.toString();
    }
}
