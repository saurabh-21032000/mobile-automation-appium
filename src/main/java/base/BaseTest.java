package base;

import com.aventstack.extentreports.ExtentReports;
import core.AppiumServerManager;
import core.DriverManager;
import core.ExtentManager;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ScreenshotUtils;

@Listeners(TestListener.class)
public class BaseTest {
  protected AppiumDriver driver;
  protected ExtentReports extent;

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    extent = ExtentManager.getReporter();
  }

  @Parameters({ "platform", "deviceName" })
  @BeforeMethod(alwaysRun = true)
  public void setUp(@Optional("android") String platform, @Optional("emulator-5554") String deviceName) {
    // Start Appium server (optional - you can manage Appium server externally)
    AppiumServerManager.startServer();
    driver = DriverManager.initDriver(platform, deviceName);
    // Make AssertionHelper aware of the current driver so it can capture screenshots on assertion failures
    utils.AssertionHelper.setDriver(driver);
    // Handle common runtime permission dialog(s) as early as possible using SignupPage helpers
    try {
      pages.android.SignupPage startup = new pages.android.SignupPage(driver);
      startup.clickAllowPermission();
      // Some flows show a calling-account list after permission — try clicking first item
      startup.clickCallingAccountListItem();
    } catch (Exception ignored) {
      // Permission dialog may not be present; continue
    }
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) {
    try {
      if (result.getStatus() == ITestResult.FAILURE) {
        String path = ScreenshotUtils.capture(driver, result.getName());
        ExtentManager.getTest().fail(result.getThrowable()).addScreenCaptureFromPath(path);
        // Attempt to open screenshot automatically on macOS for local debugging
        try {
          if (path != null && System.getProperty("os.name").toLowerCase().contains("mac")) {
            Runtime.getRuntime().exec(new String[] {"open", path});
          }
        } catch (Exception ignored) {
          // Non-fatal: opening screenshot is a convenience for local runs
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DriverManager.quitDriver();
      // Clear static driver reference in AssertionHelper
      utils.AssertionHelper.setDriver(null);
      AppiumServerManager.stopServer();
    }
  }

  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
    if (extent != null)
      extent.flush();
  }
}