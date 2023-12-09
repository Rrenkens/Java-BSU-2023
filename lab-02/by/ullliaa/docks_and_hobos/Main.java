package by.ullliaa.docks_and_hobos;

import by.ullliaa.docks_and_hobos.utilities.Controller;
import by.ullliaa.docks_and_hobos.utilities.ReaderData;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }

        Controller controller = new Controller(ReaderData.readData(args[0]));
        controller.start();
    }
}