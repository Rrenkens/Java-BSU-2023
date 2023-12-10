package by.Dzenia.docks_and_hobos;

import by.Dzenia.docks_and_hobos.Controller.Program;

import java.io.IOException;
public class Main {

//    void shipGeneratorCheck(String pathToFile) {
//        ShipGenerator generator = new ShipGenerator()
//    }
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);
        if (args.length != 1) {
            throw new IllegalArgumentException("Must be only one arg!");
        }
        Program program = new Program(args[0]);
        program.start();
    }
}
