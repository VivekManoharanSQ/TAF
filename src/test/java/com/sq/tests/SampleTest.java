package com.sq.tests;

import com.aventstack.extentreports.Status;
import com.sq.core.DataManager;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import com.sq.utils.Screenshot;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SampleTest {

    @Test(testName = "Sample Test1")
    @CustomAttribute(name = "category", values = {"Login"})
//    @Parameters("edge")
    public void sampleTestMethod1(@Optional String browserType) {
        System.out.println(DataManager.getDataMap());
        DriverManager.getDriver().get("https://testng.org/doc/");
        AssertHelper assertHelper = new AssertHelper();
        ReportManager.getExtentTest().log(Status.INFO, "test");
        assertHelper.assertEquals(1, 12, "Verify the numbers");
        assertHelper.assertEquals(1, 1, "Verify the numbers");
        assertHelper.assertAll();

    }

    @Test(testName = "Sample Test 2")
    @CustomAttribute(name = "category", values = {"Login", "smoke"})
    public void sampleTestMethod2() {
        DriverManager.getDriver().get("https://testng.org/doc/");
        AssertHelper assertHelper = new AssertHelper();
        ReportManager.getExtentTest().log(Status.INFO, "test", Screenshot.attachScreenShot());
        assertHelper.assertEquals(1, 1, "Verify the numbers");
        assertHelper.assertEquals(1, 10, "Verify the numbers");
        assertHelper.assertAll();
    }
}
