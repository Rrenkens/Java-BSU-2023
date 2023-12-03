package by.waitingsolong.docks_and_hobos.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadName {
    private static final Logger logger = LogManager.getLogger(ReadName.class);

    public List<String> readNames(String fileName) {
        List<String> names = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                names.addAll(Arrays.asList(line.split(" ")));
            }
        } catch (IOException e) {
            logger.error("Error reading file", e);
            throw new RuntimeException(e);
        }
        return names;
    }
}
