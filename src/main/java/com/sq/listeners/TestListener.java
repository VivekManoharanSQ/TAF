package com.sq.listeners;

import com.aventstack.extentreports.Status;
import com.sq.constants.TafConstants;
import com.sq.core.DataManager;
import com.sq.core.DriverFactory;
import com.sq.core.DriverManager;
import com.sq.core.ReportManager;
import com.sq.datahandler.ExcelReader;
import com.sq.enums.BrowserType;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.annotations.CustomAttribute;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    private ReportManager reportManager;
    private ExcelReader excelReader = null;

    private Map<String, Map<String, String>> mapOfSheets = new HashMap<>();

    @Override
    public void onFinish(ISuite suite) {
        reportManager.flushReport();
    }

    @Override
    @Synchronized
    public void onStart(ITestContext context) {
        System.out.println("I am in itest context ");
        Map<String, String> xmlParams = context.getCurrentXmlTest().getAllParameters();
        TafConstants.setExecutionParams(xmlParams);
        if (reportManager == null)
            reportManager = new ReportManager();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        DriverManager.getDriver().quit();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        DriverManager.getDriver().quit();
        ReportManager.getExtentTest().log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        DriverManager.getDriver().quit();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        boolean isParameterPresent = isAnnotationPresent(method, Parameters.class);
        String[] params = isParameterPresent ? method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Parameters.class).value() : new String[]{};
        String browserType = TafConstants.get("browserType");
        if (isParameterPresent && params.length > 0) {
            // First parameter is the browser
            LOGGER.info("Setting browser from parameter");
            browserType = params[0];
        }
        checkBrowser(browserType);
        DriverFactory.setDriver(browserType);

        if (TafConstants.get("dataSource").equalsIgnoreCase("excel")) {
            excelReader = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/datafiles/" +
                    TafConstants.get("dataFileName"));
            mapOfSheets = excelReader.getSheetAsMap(TafConstants.get("dataSheetName"));
        }
        assignTestData(method);

        createExtentTest(browserType,method, testResult);

        DriverManager.getDriver().get(TafConstants.get("url"));
    }

    private void checkBrowser(String browserType) {
        if (!Arrays.toString(BrowserType.values()).contains(browserType)) {
            LOGGER.error("Incorrect browserType --> " + browserType + "\n Possible values are " + Arrays.toString(BrowserType.values()));
            System.exit(1);
        }
    }

    private void createExtentTest(String browserType, IInvokedMethod method, ITestResult testResult) {
        String testName = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
        testName = testName != null ? testName : method.getTestMethod().getMethodName();
        boolean isCustomAttributePresent = isAnnotationPresent(method, CustomAttribute.class);
        String[] category = new String[]{};
        if (isCustomAttributePresent) {
            String name = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(CustomAttribute.class).name();
            if (name.equalsIgnoreCase("category")) {
                category = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(CustomAttribute.class).values();
            }
        }
        reportManager.createExtentTest(browserType,testName, category);
    }

    private boolean isAnnotationPresent(IInvokedMethod method, Class clazz) {
        return Arrays.stream(method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotations()).anyMatch(annotation -> annotation.annotationType().equals(clazz));
    }

    private void assignTestData(IInvokedMethod method) {
        String methodName = method.getTestMethod().getMethodName();
        if (mapOfSheets.containsKey(methodName)) {
            DataManager.setDataMap(mapOfSheets.get(methodName));
        }
    }
}
