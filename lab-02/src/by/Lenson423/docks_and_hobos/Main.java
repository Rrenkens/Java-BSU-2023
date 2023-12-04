package by.Lenson423.docks_and_hobos;

import by.Lenson423.docks_and_hobos.utilities.Controller;
import by.Lenson423.docks_and_hobos.utilities.JsonToControllerConverter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Illegal number of arguments");
        }
        Controller controller = JsonToControllerConverter.readJsonAndGetController(args[0]);
        controller.startWorking();
    }
}