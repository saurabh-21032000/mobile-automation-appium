package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
  private static ExtentReports extent;
  private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

  public synchronized static ExtentReports getReporter() {
    if (extent == null) {
      String path = ConfigManager.get("report.path");
      ExtentSparkReporter reporter = new ExtentSparkReporter(path + "index.html");
      extent = new ExtentReports();
      extent.attachReporter(reporter);
    }
    return extent;
  }

  public static void setTest(ExtentTest t) {
    test.set(t);
  }

  public static ExtentTest getTest() {
    return test.get();
  }
}
