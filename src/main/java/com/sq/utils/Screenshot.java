package com.sq.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.sq.core.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Screenshot {

    public static Media attachScreenShot() {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64)).build();
    }

    public static Media attachScreenShot(String title) {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64), title).build();
    }
}
