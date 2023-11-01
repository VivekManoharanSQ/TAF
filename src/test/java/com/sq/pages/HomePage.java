package com.sq.pages;

import com.sq.core.WebUI;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class HomePage extends WebUI {

    By link_documentation = By.linkText("Documentation");
    By link_loggingInegration = By.linkText("10 - Logging framework integration in TestNG");
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    @SneakyThrows
    public void clickDocumentationLink() {

//        driver.get("chrome://settings/");
//        ((JavascriptExecutor)driver).executeScript("chrome.settingsPrivate.setDefaultZoom(0.7);");

        ((JavascriptExecutor) driver).executeScript("document.body.style.transform='scale(0.8)'");
        driver.get("https://testng.org/doc/");
        Thread.sleep(10000);
        click(link_documentation, "Documentation link");
        click(link_loggingInegration);
    }
}