package core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.util.Map;

public class CapabilityManager {
  public static DesiredCapabilities getCapabilities(String platform, String deviceName) {
    try (FileReader reader = new FileReader("src/test/resources/capabilities.json")) {
      JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
      JsonObject plat = root.getAsJsonObject(platform.toLowerCase());
      DesiredCapabilities caps = new DesiredCapabilities();
      for (Map.Entry<String, com.google.gson.JsonElement> entry : plat.entrySet()) {
        String key = entry.getKey();
        // Skip any deviceName entries from JSON to avoid duplicate keys (deviceName vs appium:deviceName)
        if (key.equalsIgnoreCase(MobileCapabilityType.DEVICE_NAME) || key.equalsIgnoreCase("appium:deviceName")) {
          continue;
        }
        caps.setCapability(key, entry.getValue().getAsString());
      }

      // Set deviceName only once from the runtime parameter
      caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
      return caps;
    } catch (Exception e) {
      throw new RuntimeException("Unable to load capabilities.json", e);
    }
  }
}
