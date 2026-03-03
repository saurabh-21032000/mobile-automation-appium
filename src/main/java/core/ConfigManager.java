package core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
  private static Properties props = new Properties();

  static {
    try (InputStream in = new FileInputStream("src/main/resources/config.properties")) {
      props.load(in);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load config.properties", e);
    }
  }

  public static String get(String key) {
    return props.getProperty(key);
  }

  public static int getInt(String key) {
    return Integer.parseInt(props.getProperty(key));
  }
}
