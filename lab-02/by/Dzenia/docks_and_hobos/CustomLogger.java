package by.Dzenia.docks_and_hobos;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class CustomLogger extends Logger {
    private static CustomLogger instance;

    protected CustomLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
//        setLevel(Level.CONFIG);
//        try {
//            FileHandler fileHandler = new FileHandler("lab-02/by/Dzenia/docks_and_hobos/logs/log.log");
//            fileHandler.setFormatter(new CustomFormatter());
//            addHandler(fileHandler);
//            ConsoleHandler consoleHandler = new ConsoleHandler();
//            consoleHandler.setFormatter(new CustomFormatter());
//            addHandler(consoleHandler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public static CustomLogger getLogger(String name) {
        if (instance == null) {
            instance = new CustomLogger(name, null);
            instance.configureLogger();
        }
        return instance;
    }

    private void configureLogger() {
        setLevel(Level.CONFIG);
        try {
            String logFilePath = "lab-02/by/Dzenia/docks_and_hobos/logs/log.log";
            FileHandler fileHandler = new FileHandler(logFilePath);
            fileHandler.setFormatter(new CustomFormatter());
            addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new CustomFormatter());
            addHandler(consoleHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class CustomFormatter extends Formatter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @Override
        public String format(LogRecord record) {
            return dateFormat.format(new Date(record.getMillis())) +
                    " " +
                    record.getLevel() +
                    " " +
                    record.getMessage() + "\n";
        }
    }
}
