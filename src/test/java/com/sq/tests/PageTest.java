package com.sq.tests;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Media;
import com.sq.annotations.Category;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import com.sq.stepdefs.SD_HomePage;
import com.sq.utils.Screenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PageTest {

    private ThreadLocal<String> testNames = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void ts(Object[] testData) {
//        testNames.set(testData[0] + "1");
    }

    @Test(testName = "Test_1", dataProvider = "logindata")
    @Category(categories = {"Login"})
    public void test_1(int i) {
        ReportManager.getExtentTest().log(Status.INFO, String.valueOf(i));
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
//        sdHomePage.clickOnDocumentation();
        Media m = Screenshot.attachScreenShot();

//        for(int j=0;j<=42;j++){
//            ReportManager.getExtentTest().log(Status.INFO,"Test",m);
//        }
        ReportManager.getExtentTest().log(Status.INFO, "Test", m);

        assertHelper.assertAll();
    }

    //        @Test(testName = "Test_2")
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
                {1}
//                ,
//                {2},
//                {3},{4},{5},{6},{7},{8},{9},{10},{1},
//                {2},
//                {3},{4},{5},{6},{7},{8},{9},{10},{1},
//                {2},
//                {3},{4},{5},{6},{7},{8},{9},{10},{1},
//                {2},
//                {3},{4},{5},{6},{7},{8},{9},{10},{1},
//                {2},
//                {3},{4},{5},{6},{7},{8},{9},{10}
        };

    }

    //    @Override
    public String getTestName() {
        return testNames.get();
    }
}
