package com.sq.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sq.constants.TafConstants;

public class ReportManager {

    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private final ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/" + TafConstants.get("reportPath") + "/" + TafConstants.get("reportFileName") + ".html");
    private final ExtentReports report = new ExtentReports();


    public ReportManager() {
        configureExtentReport();
    }

    public static ExtentTest getExtentTest() {
        return extentTestThreadLocal.get();
    }

    private void setExtentTest(ExtentTest extentTest) {
        extentTestThreadLocal.set(extentTest);
    }


    private void configureExtentReport() {
        reporter.config().setDocumentTitle(TafConstants.get("reportTitle"));
        reporter.config().setReportName(TafConstants.get("reportName"));
        reporter.config().thumbnailForBase64(true);
        report.attachReporter(reporter);
    }

    public void createExtentTest(String testName, String[] category) {
        setExtentTest(report.createTest(testName).assignCategory(category));
    }

    public void flushReport() {
        report.flush();
    }

}
