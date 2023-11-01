package com.sq.tests;

import com.sq.core.DriverManager;
import com.sq.helpers.AssertHelper;
import com.sq.stepdefs.SD_HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Test;

public class PageTest {

    @Test(testName = "Test_1")
    @CustomAttribute(name = "category", values = {"Login"})
    public void test_1() {
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.clickOnDocumentation();
        assertHelper.assertAll();
    }
}
