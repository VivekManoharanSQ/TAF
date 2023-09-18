package com.sq;

import com.aventstack.extentreports.Status;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import org.testng.annotations.Test;

public class SampleTest {

    @Test(testName = "Sample Test1")
    public void sampleTestMethod1() {
        DriverManager.getDriver().get("https://testng.org/doc/");
        AssertHelper assertHelper = new AssertHelper();
        ReportManager.getExtentTest().log(Status.INFO, "test");
        assertHelper.assertEquals(1, 12, "Verify the numbers");
        assertHelper.assertEquals(1, 1, "Verify the numbers");
        assertHelper.assertAll();
    }

    @Test(testName = "Sample Test 2")
    public void sampleTestMethod2() {
        DriverManager.getDriver().get("https://testng.org/doc/");
        AssertHelper assertHelper = new AssertHelper();
        ReportManager.getExtentTest().log(Status.INFO, "test");
        assertHelper.assertEquals(1, 1, "Verify the numbers");
        assertHelper.assertEquals(1, 10, "Verify the numbers");
        assertHelper.assertAll();
    }
}
