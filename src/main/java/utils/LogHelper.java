package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * LogHelper — Centralized logger with colorized console output and per-test log clearing.
 * Works with Log4j2 configuration (logs/app.log). 
 */
public class LogHelper {

    private static final Logger logger = LogManager.getLogger(LogHelper.class);
    private static final String LOG_FILE_PATH = "logs/app.log";

    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";

    /** Generic log methods */
    public static void info(String message) {
        logger.info(message);
        System.out.println(GREEN + "[INFO] " + message + RESET);
    }

    public static void warn(String message) {
        logger.warn(message);
        System.out.println(YELLOW + "[WARN] " + message + RESET);
    }

    public static void debug(String message) {
        logger.debug(message);
        System.out.println(BLUE + "[DEBUG] " + message + RESET);
    }

    public static void error(String message) {
        logger.error(message);
        System.err.println(RED + "[ERROR] " + message + RESET);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
        System.err.println(RED + "[ERROR] " + message + " - " + throwable.getMessage() + RESET);
    }

    /** Clears previous test log file */
    private static void clearLogFile() {
        try {
            Path logPath = Paths.get(LOG_FILE_PATH);
            Files.createDirectories(logPath.getParent());
            Files.deleteIfExists(logPath);
            Files.createFile(logPath);
            System.out.println(CYAN + "🧹 Cleared existing log file: " + logPath.toAbsolutePath() + RESET);
        } catch (IOException e) {
            System.err.println(RED + "⚠️ Unable to clear log file: " + e.getMessage() + RESET);
        }
    }

    /** Marks the start of a new test with optional data */
    public static void startTest(String testName) {
        startTest(testName, null);
    }

    public static void startTest(String testName, Map<String, String> data) {
        clearLogFile();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        info("========================================================");
        info(MAGENTA + "🧪 STARTING TEST: " + testName + RESET);
        info("⏰ " + timestamp);
        if (data != null && !data.isEmpty()) {
            info("🧾 Test Data: " + data);
        }
        info("========================================================");
    }

    /** Step log — for intermediate test actions */
    public static void step(String message) {
        info("➡️  " + message);
    }

    /** Marks the end of the test */
    public static void endTest(String testName) {
        info("========================================================");
        info("🏁 TEST COMPLETED: " + testName);
        info("========================================================");
    }
}
