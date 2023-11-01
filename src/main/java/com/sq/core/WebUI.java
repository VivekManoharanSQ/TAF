package com.sq.core;

import com.sq.constants.TafConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class WebUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUI.class);

    private WebDriver driver;

    private WebElement element;

    private long timeOut = Long.parseLong(TafConstants.get("explicitWait"));

    public WebUI(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            boolean check = verifyElementVisible(by, timeOut);
            if (check) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementToTop(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            LOGGER.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    public void sendKeys(By by, String textToEnter, String... elementName) {
        findElement(by).sendKeys(textToEnter);
        logEventStep("Entered text \"" + textToEnter + "\" into ", by, elementName);
    }

    public void click(By by, String... elementName) {
        findElement(by).click();
        logEventStep("Clicked on the element ", by, elementName);
    }

    public String getText(By by, String... elementName) {
        String text = findElement(by).getText();
        logEventStep("Fetched text \"" + text + "\" from ", by, elementName);
        return text;
    }

    public String getAttribute(By by, String attributeName, String... elementName) {
        String attributeValue = findElement(by).getAttribute(attributeName);
        logEventStep("Fetched  " + attributeName + " = \"" + attributeValue + "\" from ", by, elementName);
        return attributeName;
    }

    public void executeJavaScript(String script, Object... args) {
        ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public Select getSelect(By by) {
        return new Select(findElement(by));
    }

    public void selectOptionByValue(By by, String value) {
        getSelect(by).selectByValue(value);
    }

    public void selectOptionByIndex(By by, int index) {
        getSelect(by).selectByIndex(index);
    }

    public void selectOptionByVisibleText(By by, String visibleText) {
        getSelect(by).selectByValue(visibleText);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public <T> T waitForElement(ExpectedCondition<T> condition, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        return wait.until(condition);
    }

    public void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) Objects.requireNonNull(driver)).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LOGGER.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load (Javascript). (" + timeOut + "s)");
            }
        }
    }

    public boolean verifyElementVisible(By by, long timeout) {
        waitForPageLoaded();

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LOGGER.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            LOGGER.error("The element is not visible. " + e.getMessage());
            Assert.fail("The element is NOT visible. " + by);
            return false;
        }
    }

    public void scrollToElementToTop(By by) {
        waitForPageLoaded();
        System.out.println("Scrolling Down");
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", findElement(by));
        LOGGER.info("Scroll to element " + by);
    }

    public void logEventStep(String event, By by, String... elementName) {
        if (elementName.length > 0)
            LOGGER.info("Clicked on the " + elementName[0]);
        if (ReportManager.getExtentTest() != null) {
            if (elementName.length > 0)
                ReportManager.getExtentTest().pass(event + elementName[0]);
            else
                ReportManager.getExtentTest().pass(event + by);
        }
    }
}
