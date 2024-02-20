package com.sq.core;

import com.sq.constants.TafConstants;
import com.sq.enums.BrowserType;
import com.sq.enums.DeviceFarmType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class DriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);

    public static void setDriver(String browserType) {
        WebDriver driver = (Objects.requireNonNull(TafConstants.get("runRemote")).equalsIgnoreCase("true") ? createDriver(browserType, TafConstants.get("hubUrl")) : createDriver(browserType));
        DriverManager.getThreadDriver().set(driver);
    }

    @SneakyThrows
    private static WebDriver createDriver(String browserType) {
        switch (BrowserType.valueOf(browserType)) {
            case chrome -> {
                return new ChromeDriver(getChromeOption());
            }
            case edge -> {
                return new EdgeDriver(getEdgeOption());
            }
            case firefox -> {
                return new FirefoxDriver(getFirefoxOption());
            }
            case safari -> {
                return new SafariDriver(getSafariOption());
            }
            case chromemobile -> {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                ChromeOptions options = getChromeOption();
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

    @SneakyThrows
    private static WebDriver createDriver(String browserType, String remoteUrl) {
        DesiredCapabilities capabilities = getDeviceFarmCapabilities(browserType);
        switch (BrowserType.valueOf(browserType)) {
            case chrome -> {
                ChromeOptions chromeOptions = getChromeOption().merge(capabilities);
                return new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
            }
            case edge -> {
                return new RemoteWebDriver(new URL(remoteUrl), getEdgeOption().merge(capabilities));
            }
            case firefox -> {
                return new RemoteWebDriver(new URL(remoteUrl), getFirefoxOption().merge(capabilities));
            }
            case safari -> {
                return new RemoteWebDriver(new URL(remoteUrl), getSafariOption().merge(capabilities));
            }
            case chromemobile -> {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                ChromeOptions options = new ChromeOptions();
                options.addArguments(Objects.requireNonNull(TafConstants.get("chrome.options")).split(","));
                options.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new RemoteWebDriver(new URL(remoteUrl), options.merge(capabilities));
            }
            case androidnative -> {
                capabilities.merge(mapToCapabilities(propsToMap("android.")));
                capabilities.setCapability("automationName", AutomationName.ANDROID_UIAUTOMATOR2);
                return new AndroidDriver(new URL(remoteUrl), capabilities);
            }
            case iosnative -> {
                capabilities.merge(mapToCapabilities(propsToMap("ios.")));
                capabilities.setCapability("automationName", AutomationName.IOS_XCUI_TEST);
                return new AndroidDriver(new URL(remoteUrl), capabilities);
            }
            default -> {
                LOGGER.error("Incorrect browserType --> " + browserType + "\n Possible values are " + Arrays.toString(BrowserType.values()));
                System.exit(1);
                return null;
            }
        }
    }


    private static ChromeOptions getChromeOption() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Objects.requireNonNull(TafConstants.get("chrome.options")).split(","));
        options.merge(mapToCapabilities(propsToMap("chrome.")));
        return options;
    }

    private static EdgeOptions getEdgeOption() {
        return new EdgeOptions().addArguments(Objects.requireNonNull(TafConstants.get("edge.options")).split(","));
    }

    private static FirefoxOptions getFirefoxOption() {
        return new FirefoxOptions().addArguments(Objects.requireNonNull(TafConstants.get("firefox.options")).split(","));
    }

    private static SafariOptions getSafariOption() {
        return new SafariOptions();
    }

    private static DesiredCapabilities getDeviceFarmCapabilities(String browserType) {
        String deviceFarmName = TafConstants.get("deviceFarm");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (DeviceFarmType.valueOf(deviceFarmName)) {
            case browserstack -> {
                capabilities.setCapability("bstack:options", propsToMap("browserstackoptions"));
            }
            case lambdatest -> {
                capabilities.setCapability("lambda:options", propsToMap("lambda.options"));

            }
            case seetest -> {
            }
        }
        return capabilities;
    }

    private static DesiredCapabilities getCapabilities(String browserCaps) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Arrays.stream(TafConstants.get(browserCaps).split(","))
                .filter(caps -> !caps.isEmpty())
                .forEach(cap -> capabilities.setCapability(cap.split("=")[0], cap.split("=")[1]));
        return capabilities;
    }


    private static Map<String, String> propsToMap(String capsPrefix) {
        return TafConstants.getAllKeyContains(capsPrefix).entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().split("\\.")[1], Map.Entry::getValue));
    }

    private static DesiredCapabilities mapToCapabilities(Map<String, String> browserCaps) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        for (Map.Entry<String, String> e : browserCaps.entrySet()) {
            capabilities.setCapability(e.getKey(), e.getValue());
        }
        return capabilities;
    }
}


