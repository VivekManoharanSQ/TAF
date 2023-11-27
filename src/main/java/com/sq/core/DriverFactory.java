package com.sq.core;

import com.sq.constants.TafConstants;
import com.sq.enums.BrowserType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobilePlatform;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);

    public static void setDriver(String browserType) {
        WebDriver driver = (Objects.requireNonNull(TafConstants.get("runRemote")).equalsIgnoreCase("true") ? createDriver(browserType, "") : createDriver(browserType));
        DriverManager.getThreadDriver().set(driver);
    }

    @SneakyThrows
    private static WebDriver createDriver(String browserType) {
        switch (BrowserType.valueOf(browserType)) {
            case chrome -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments(Objects.requireNonNull(TafConstants.get("chrome.options")).split(","));
                return new ChromeDriver(options);
            }
            case edge -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments(Objects.requireNonNull(TafConstants.get("edge.options")).split(","));
                return new EdgeDriver(options);
            }
            case firefox ->{
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(Objects.requireNonNull(TafConstants.get("firefox.options")).split(","));
                return new FirefoxDriver(options);
            }
            case safari -> {
                SafariOptions options = new SafariOptions();
                return new SafariDriver(options);
            }
            case chromemobile -> {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                ChromeOptions options = new ChromeOptions();
                options.addArguments(Objects.requireNonNull(TafConstants.get("chrome.options")).split(","));
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(options);
            }
            default -> {
                LOGGER.error("Incorrect browserType --> " + browserType + "\n Possible values are " + Arrays.toString(BrowserType.values()));
                System.exit(1);
                return null;
            }
        }
    }

    private static WebDriver createDriver(String browserType, String remoteUrl) {
        return new ChromeDriver();
    }

    private static DesiredCapabilities getCapabilities(String browserCaps) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Arrays.stream(TafConstants.get(browserCaps).split(","))
                .filter(caps -> !caps.isEmpty())
                .forEach(cap -> capabilities.setCapability(cap.split(":")[0], cap.split(":")[1]));
        return capabilities;
    }
}


