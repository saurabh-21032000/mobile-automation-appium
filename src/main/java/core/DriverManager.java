package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class DriverManager {
  private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

  public static AppiumDriver initDriver(String platform, String deviceName) {
    try {
      DesiredCapabilities caps = CapabilityManager.getCapabilities(platform, deviceName);
      AppiumDriver drv;
      if (platform.equalsIgnoreCase("android")) {
        drv = new AndroidDriver(new URL(AppiumServerManager.getServerUrl()), caps);
      } else {
        throw new IllegalArgumentException("Only Android supported in this skeleton");
      }
      driver.set(drv);
      return driver.get();
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize driver", e);
    }
  }

  public static AppiumDriver getDriver() {
    return driver.get();
  }

  public static void quitDriver() {
    if (driver.get() != null) {
      driver.get().quit();
      driver.remove();
    }
  }
}
