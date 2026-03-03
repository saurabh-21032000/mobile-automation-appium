package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AssertionHelper — Centralized assertions with screenshot capture on failure.
 * Works in sync with LogHelper and AppiumDriver context.
 */
public class AssertionHelper {

    private static AppiumDriver driver;

    /** Inject driver (called in BaseTest.setup()) */
    public static void setDriver(AppiumDriver driverInstance) {
        driver = driverInstance;
        LogHelper.debug("🔧 [AssertionHelper] Driver initialized: " + (driver != null));
    }

    /** Capture screenshot */
    private static String captureScreenshot(String testName) {
        if (driver == null) {
            LogHelper.error("⚠️ Cannot take screenshot — driver is NULL!");
            return null;
        }

        try {
            String projectPath = System.getProperty("user.dir");
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotDir = projectPath + "/screenshots/";
            new File(screenshotDir).mkdirs();
            String filePath = screenshotDir + testName + "_" + timestamp + ".png";

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);

            LogHelper.info("📸 Screenshot captured at: " + filePath);
            // On macOS, open the screenshot automatically to aid local debugging
            try {
                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec(new String[] {"open", filePath});
                }
            } catch (Exception ignored) {
                // Non-fatal convenience
            }
            return filePath;
        } catch (Exception e) {
            LogHelper.error("❌ Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }

        /** Public wrapper so other helpers can trigger screenshots when needed */
        public static String takeScreenshot(String testName) {
            return captureScreenshot(testName);
        }

    /** ✅ assertTrue with screenshot */
    public static void assertTrue(boolean condition, String message) {
        if (condition) {
            LogHelper.info("✅ ASSERTION PASSED: " + message);
        } else {
            LogHelper.error("❌ ASSERTION FAILED: " + message);
            captureScreenshot("assertTrueFailure");
        }
        Assert.assertTrue(condition, message);
    }

    /** ✅ assertFalse with screenshot */
    public static void assertFalse(boolean condition, String message) {
        if (!condition) {
            LogHelper.info("✅ ASSERTION PASSED (false as expected): " + message);
        } else {
            LogHelper.error("❌ ASSERTION FAILED (expected false but got true): " + message);
            captureScreenshot("assertFalseFailure");
        }
        Assert.assertFalse(condition, message);
    }

    /** ✅ assertEquals with screenshot */
    public static void assertEquals(Object actual, Object expected, String message) {
        if (expected.equals(actual)) {
            LogHelper.info("✅ ASSERTION PASSED: " + message + " | Expected=" + expected + " | Actual=" + actual);
        } else {
            LogHelper.error("❌ ASSERTION FAILED: " + message + " | Expected=" + expected + " | Actual=" + actual);
            captureScreenshot("assertEqualsFailure");
        }
        Assert.assertEquals(actual, expected, message);
    }

    /** ❌ Fail test explicitly */
    public static void fail(String message) {
        fail(message, null);
    }

    /** ❌ Fail test explicitly with throwable (better diagnostics) */
    public static void fail(String message, Throwable t) {
        LogHelper.error("❌ TEST FAILED EXPLICITLY: " + message);

        if (t != null) {
            // Log throwable message and stacktrace to logs
            LogHelper.error("❌ Failure reason: " + t.toString());
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            LogHelper.error(sw.toString());

            // Also emit to stderr so console output clearly shows where it failed
            System.err.println("❌ TEST FAILED: " + message + "\nReason: " + t.toString());
            t.printStackTrace();
        } else {
            System.err.println("❌ TEST FAILED: " + message);
        }

        captureScreenshot("explicitFailure");
        Assert.fail(message);
    }
}
