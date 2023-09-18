package com.sq.listeners;

import com.sq.constants.TafConstants;
import com.sq.core.DriverFactory;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.enums.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

public class TestListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    private final ReportManager reportManager = new ReportManager();


    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onFinish(ISuite suite) {
        reportManager.flushReport();
    }

    @Override
    public void onStart(ITestContext context) {
        Map<String, String> xmlParams = context.getCurrentXmlTest().getAllParameters();
        TafConstants.setExecutionParams(xmlParams);
        System.out.println(xmlParams.values());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        DriverManager.getDriver().quit();

    }

    @Override
    public void onTestFailure(ITestResult result) {
        DriverManager.getDriver().quit();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        DriverManager.getDriver().quit();
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        checkBrowser();
        DriverFactory.createDriver();
        String testName = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
        testName = testName != null ? testName : method.getTestMethod().getMethodName();
        reportManager.createExtentTest(testName);
    }

    private void checkBrowser() {
        String browserType = TafConstants.get("browserType");
        if (!Arrays.toString(BrowserType.values()).contains(browserType)) {
            LOGGER.error("Incorrect browserType --> " + browserType + "\n Possible values are " + Arrays.toString(BrowserType.values()));
            System.exit(1);
        }
    }
}
