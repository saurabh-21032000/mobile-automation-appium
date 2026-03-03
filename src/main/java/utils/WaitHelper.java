package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {
  public static void waitForVisible(AppiumDriver driver, By locator, int timeoutSeconds) {
    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public static void waitForClickable(AppiumDriver driver, By locator, int timeoutSeconds) {
    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(ExpectedConditions.elementToBeClickable(locator));
  }

  public static WebElement waitForElement(AppiumDriver driver, By locator, int timeoutSeconds) {
    return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
        .until(ExpectedConditions.presenceOfElementLocated(locator));
  }
}
