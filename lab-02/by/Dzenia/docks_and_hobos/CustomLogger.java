package by.Dzenia.docks_and_hobos;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class CustomLogger extends Logger {
    private static CustomLogger instance;
    private static final int FILE_COUNT = 5;
    private static final int MAX_FILE_SIZE_BYTES = 100_000;
    protected CustomLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }
    public static CustomLogger getLogger(String name) {
        if (instance == null) {
            instance = new CustomLogger(name, null);
            instance.configureLogger();
        }
        return instance;
    }

    private String getLogFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        return "lab-02/by/Dzenia/docks_and_hobos/logs/log_" + timestamp + ".log";
    }
    private void configureLogger() {
        setLevel(Level.CONFIG);
        try {
            // Используем FileHandler с шаблоном времени для имени файла
            String logFilePath = getLogFileName();
            FileHandler fileHandler = new FileHandler(logFilePath, MAX_FILE_SIZE_BYTES, FILE_COUNT, false);
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
