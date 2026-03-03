package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import core.ConfigManager;
import core.DriverManager;

public class WaitUtils {
  public static WebElement waitForVisibility(WebElement el) {
    AppiumDriver driver = DriverManager.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(ConfigManager.getInt("explicit.wait")));
    return (WebElement) wait.until(ExpectedConditions.visibilityOf(el));
  }
}
