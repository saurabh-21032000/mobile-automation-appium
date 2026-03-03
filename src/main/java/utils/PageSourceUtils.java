package utils;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageSourceUtils {
  public static String save(AppiumDriver driver, String name) {
    try {
      String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
      String dir = System.getProperty("user.dir") + "/logs/pagesource/";
      new File(dir).mkdirs();
      String filePath = dir + name + "_" + timestamp + ".xml";
      String src = driver.getPageSource();
      Files.writeString(Paths.get(filePath), src);
      LogHelper.info("📄 Page source saved: " + filePath);
      return filePath;
    } catch (Exception e) {
      LogHelper.error("❌ Failed to save page source: " + e.getMessage());
      return null;
    }
  }
}
