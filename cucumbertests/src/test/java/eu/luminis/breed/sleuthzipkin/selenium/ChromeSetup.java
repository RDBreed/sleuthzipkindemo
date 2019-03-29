package eu.luminis.breed.sleuthzipkin.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.logging.Level;

public class ChromeSetup {

    public static ChromeDriver getChromeDriverWithLogging() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        options.setCapability(ChromeOptions.CAPABILITY, options);
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return new ChromeDriver(options);
    }
}
