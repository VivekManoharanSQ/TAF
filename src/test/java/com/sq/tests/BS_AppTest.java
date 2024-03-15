package com.sq.tests;

import com.sq.core.DriverManager;
import com.sq.utils.Direction;
import com.sq.utils.FingerGestureUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BS_AppTest {


    private static By txtYourAgentCode = By.xpath("//*[@text='Your Agent Code']");

    private static By txtYourPassword = By.xpath("//*[@text='Your Password']");

    private static By btnLogin = By.xpath("//*[@text='LOGIN']");


    @Test(testName = "BS App Test")
    public void sampleBSTest() throws InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        driver.findElement(By.xpath("//*[@text='Use Testdroid Cloud']")).click();
        driver.findElement(By.xpath("//*[@text='Please type your name to proceed']")).sendKeys("Skill Quotient Demo");
        Thread.sleep(10000);
    }

    public static void main(String[] a) throws MalformedURLException, InterruptedException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "OnePlus 7T");
        caps.setCapability("platformName", "android");
        caps.setCapability("interactiveDebugging", true);
        caps.setCapability("app", "bs://56c29a2122299598009dcd560a61c6dcb0adf023");
        caps.setCapability("automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability("platformVersion", "10.0");
        WebDriver driver = new AndroidDriver(new URL("https://skillquotient_L2l6gB:b6axfXNe6QHp8HMmU5nq@hub-cloud.browserstack.com/wd/hub"), caps);
        Thread.sleep(10000);
        driver.findElement(txtYourAgentCode).sendKeys("K7373");
        driver.findElement(txtYourPassword).sendKeys("pass123");
        driver.findElement(btnLogin).click();
        Thread.sleep(30000);
        List<WebElement>  nudgets = driver.findElements(By.id("com.aia.lifeplanner.stg:id/tv_title"));
        System.out.println(nudgets.size());

        FingerGestureUtils fingerGestureUtils = new FingerGestureUtils((AndroidDriver) driver);
        fingerGestureUtils.swipe(Direction.LEFT,nudgets.get(0),99);
        Thread.sleep(10000);
        driver.quit();
    }

}
