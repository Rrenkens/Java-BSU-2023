package by.kirilbaskakov.dock_and_hobos;

import java.util.logging.*;

public class MyLogger {
    private static MyLogger instance;
    private Logger logger;
    private FileHandler fileHandler;

    private MyLogger() {
        // Инициализация логгера
        logger = Logger.getLogger("MyLogger");
        logger.setUseParentHandlers(false); // Отключаем вывод в консоль

        // Создаем файловый обработчик
        try {
            String logsPath = "src/logs/";
            String fileName = generateRandomFileName();
            fileHandler = new FileHandler(logsPath + fileName);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized MyLogger getInstance() {
        if (instance == null) {
            instance = new MyLogger();
        }
        return instance;
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warning(String message) {
        logger.warning(message);
    }

    public void error(String message) {
        logger.severe(message);
    }

    private String generateRandomFileName() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString() + ".log";
    }
}