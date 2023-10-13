package com.sq.core;

import com.sq.constants.TafConstants;
import com.sq.enums.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }
            case edge -> {
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
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

}
