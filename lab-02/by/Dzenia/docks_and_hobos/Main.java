package by.Dzenia.docks_and_hobos;
import by.Dzenia.docks_and_hobos.Controller.Program;
import java.io.IOException;
import java.util.logging.*;
public class Main {
    private static final Logger logger = CustomLogger.getLogger("all");
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must be only one arg!");
        }
        logger.log(Level.CONFIG, "Path=" + args[0]);
        Program program = new Program(args[0]);
        program.start();
    }
}
