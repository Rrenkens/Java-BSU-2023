package by.Dzenia.docks_and_hobos;

import by.Dzenia.docks_and_hobos.Controller.Program;

import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must be only one arg!");
        }
        Program program = new Program(args[0]);
        program.start();
    }
}
