package com.sq.core;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    @Setter
    @Getter
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return threadDriver.get();
    }
}
