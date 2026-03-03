package core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import java.io.File;

public class AppiumServerManager {
    private static AppiumDriverLocalService service;

    public static void startServer() {
        if (service == null || !service.isRunning()) {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .usingAnyFreePort()
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    // ✅ Appium valid level, not "none"
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                    // ✅ Redirect logs to null device (Windows uses NUL)
                    .withLogFile(new File("NUL"));

            service = AppiumDriverLocalService.buildService(builder);
            service.start();
        }
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

    public static String getServerUrl() {
        if (service != null && service.isRunning()) {
            return service.getUrl().toString();
        }
        throw new RuntimeException("Appium server is not running");
    }
}
