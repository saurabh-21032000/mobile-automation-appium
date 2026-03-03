package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
  public static String capture(AppiumDriver driver, String name) {
    try {
      File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
      String dest = "reports/screenshots/" + name + "_" + timestamp + ".png";
      File destFile = new File(dest);
      destFile.getParentFile().mkdirs();
      FileUtils.copyFile(src, destFile);
      return dest;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
