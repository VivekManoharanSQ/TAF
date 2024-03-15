package com.sq.tests;

import com.aventstack.extentreports.Status;
import com.sq.annotations.Category;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import com.sq.stepdefs.SD_HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PageTest implements ITest {

    private ThreadLocal<String> testNames = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void ts(Object[] testData) {
//        testNames.set(testData[0] + "1");
    }

    @Test(testName = "Test_1")
    @Category(categories = {"Login"})
    public void test_1() {
//        ReportManager.getExtentTest().log(Status.INFO, n1 + n2);
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.clickOnDocumentation();
        assertHelper.assertAll();
    }

        @Test(testName = "Test_2")
    @CustomAttribute(name = "category", values = {"Login", "smoke"})
    public void test_2() {
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.clickOnDocumentation();
        assertHelper.assertAll();
    }

    @DataProvider(name = "logindata")
    public Object[][] dataPovider() {
        return new Object[][]{
                {"Cedric", 36},
                {"Anne", 37},
        };

    }

    @Override
    public String getTestName() {
        return testNames.get();
    }
}
