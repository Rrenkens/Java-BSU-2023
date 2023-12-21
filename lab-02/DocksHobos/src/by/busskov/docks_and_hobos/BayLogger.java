package by.busskov.docks_and_hobos;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.*;

public class BayLogger implements Runnable {
    public BayLogger(
            Level consoleLevel,
            Level fileLevel,
            int fileUpdateTime
    ) {
        this.logger = Logger.getLogger(BayLogger.class.getName());
        logger.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(consoleLevel);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        File logsDir = new File("logs");
        logsDir.mkdirs();

        try {
            fileHandler = new FileHandler("logs/log_" + LocalDateTime.now().format(fullFormatter) + ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(fileLevel);
        logger.addHandler(fileHandler);

        this.fileUpdateTime = fileUpdateTime;
        this.fileLevel = fileLevel;
    }

    @Override
    public void run() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (BayLogger.this) {
                    fileHandler.close();
                    try {
                        fileHandler = new FileHandler("logs/log_" + LocalDateTime.now().format(fullFormatter) + ".txt");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fileHandler.setFormatter(new SimpleFormatter());
                    fileHandler.setLevel(fileLevel);

                    logger.addHandler(fileHandler);
                }
            }
        }, fileUpdateTime, fileUpdateTime);
    }

    public synchronized void log(Level level, String msg) {
        logger.log(level, "{0}: {1}", new Object[]{LocalTime.now().format(timeFormatter), msg});
    }

    public synchronized void log(Level level, String msg, Object param1) {
        String result = LocalTime.now().format(timeFormatter) + ": " + msg;
        logger.log(level, result, param1);
    }

    public synchronized void log(Level level, String msg, Object params[]) {
        String result = LocalTime.now().format(timeFormatter) + ": " + msg;
        logger.log(level, result, params);
    }

    private final int fileUpdateTime;
    private final Level fileLevel;
    private final Logger logger;
    private Handler fileHandler;
    private static final DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
}
