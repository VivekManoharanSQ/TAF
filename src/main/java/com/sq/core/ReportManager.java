package com.sq.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sq.constants.TafConstants;
import com.sq.helpers.AssertHelper;
import lombok.Getter;

public class ReportManager {

    private final ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/" + TafConstants.get("reportPath") + "/" + TafConstants.get("reportFileName") + ".html");

    private final ExtentReports report = new ExtentReports();

    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();


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
        report.attachReporter(reporter);
    }

    public void createExtentTest(String testName) {
        setExtentTest(report.createTest(testName));
    }

    public void flushReport() {
        report.flush();
    }

}
