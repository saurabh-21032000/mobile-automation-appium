package utils;

import java.io.File;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
  public static Logger logger = LogManager.getLogger(LogUtils.class);
  public static void clearOldLog() {
	    try {
	        File logFile = new File("logs/test.log");
	        if (logFile.exists()) {
	            new PrintWriter(logFile).close(); // Clear file contents
	            System.out.println("🧹 Cleared old log file before new test run.");
	        }
	    } catch (Exception e) {
	        System.err.println("⚠️ Failed to clear log file: " + e.getMessage());
	    }
	}

}
