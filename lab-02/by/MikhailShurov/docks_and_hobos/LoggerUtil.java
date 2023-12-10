package by.MikhailShurov.docks_and_hobos;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static final Handler fileHandler;

    static {
        try {
            logger.setUseParentHandlers(false);
            fileHandler = new FileHandler("lab-02/logs/app.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error initializing logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}