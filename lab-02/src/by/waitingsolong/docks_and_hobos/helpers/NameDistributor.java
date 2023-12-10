package by.waitingsolong.docks_and_hobos.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameDistributor {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static List<String> uniqueNames = new ArrayList<>();
    private static int currUniqueNameIndex = 0;

    public static void readUniqueNames(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                uniqueNames.addAll(Arrays.asList(line.split(" ")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUniqueName() {
        if (uniqueNames.isEmpty()) {
            throw new RuntimeException("No unique names provided to NameDistibutor");
        }

        String name = uniqueNames.get(currUniqueNameIndex);
        currUniqueNameIndex = (currUniqueNameIndex + 1) % uniqueNames.size();
        return name;
    }

    public static String getCounterName(int counter) {
        StringBuilder nameBuilder = new StringBuilder();
        do {
            char letter = ALPHABET.charAt((int) (counter % ALPHABET.length()));
            nameBuilder.insert(0, letter);
            counter /= ALPHABET.length();
        } while (counter > 0);
        return nameBuilder.toString();
    }
}
