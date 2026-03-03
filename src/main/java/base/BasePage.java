package base;

import core.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

public class BasePage {
  protected AppiumDriver driver;

  public BasePage() {
    this.driver = DriverManager.getDriver();
    PageFactory.initElements(driver, this);
  }

  protected void click(WebElement element) {
    WaitUtils.waitForVisibility(element).click();
  }

  protected void type(WebElement element, String text) {
    WaitUtils.waitForVisibility(element).sendKeys(text);
  }
}
