package com.sq.core;

import io.appium.java_client.android.AndroidDriver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    @Setter
    @Getter(AccessLevel.PACKAGE)
    static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return threadDriver.get();
    }
}
