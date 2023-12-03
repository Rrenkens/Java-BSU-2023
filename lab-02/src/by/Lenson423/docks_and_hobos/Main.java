package by.Lenson423.docks_and_hobos;

import org.json.simple.parser.ParseException;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }
        Controller controller = JsonToControllerConverter.readJsonAndGetController(args[0]);
        controller.startWorking();
    }
}