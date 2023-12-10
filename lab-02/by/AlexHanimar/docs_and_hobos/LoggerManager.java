package by.AlexHanimar.docs_and_hobos;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.*;

import static java.lang.Thread.sleep;

public class LoggerManager implements Runnable {
    private final List<Logger> loggers;
    private final int refreshInterval;
    private final String logDir;
    private Handler handler = null;

    public LoggerManager(List<Logger> loggers, int refreshInterval, String logDir) {
        this.loggers = loggers;
        this.refreshInterval = refreshInterval;
        this.logDir = logDir;

        for (var logger : loggers) {
            logger.setUseParentHandlers(false);
        }
        var consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        addHandler(consoleHandler);
        for (var logger : loggers) {
            logger.setLevel(Level.CONFIG);
        }
        try {
            relocate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addHandler(Handler handler) {
        for (var logger : loggers) {
            logger.addHandler(handler);
        }
    }

    private void changeHandler(String filename) throws IOException {
        if (handler != null) {
            handler.flush();
        }
        for (var logger : loggers) {
            logger.removeHandler(handler);
        }
        if (handler != null) {
            handler.close();
        }
        handler = new FileHandler(filename);
        addHandler(handler);
    }

    private void relocate() throws IOException {
        File file;
        String filename;
        do {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            filename = logDir + "\\log_" + timeStamp;
            file = new File(filename);
        } while (!file.createNewFile());
        changeHandler(filename);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(refreshInterval * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                relocate();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
