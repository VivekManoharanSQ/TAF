package com.sq.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sq.constants.TafConstants;

import java.util.List;

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

    public void createExtentTest(String browserType, String testName, String[] category) {
        setExtentTest(report.createTest(testName).assignCategory(category).assignDevice(browserType));
    }

    public void flushReport() {
        report.flush();
    }

    public void splitReport() {
        int noOfTestsPerReport = Integer.parseInt(TafConstants.get("noOfTestsPerReport"));
        ExtentSparkReporter reporter;
        ExtentReports splitReport;
        List<Test> testList = report.getReport().getTestList();
        int k = 0;
        int noOfReports = testList.size() % noOfTestsPerReport == 0 ? testList.size() / noOfTestsPerReport : testList.size() / noOfTestsPerReport + 1;
        for (int j = 1; j <= noOfReports; j++) {
            splitReport = new ExtentReports();
            reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/" + TafConstants.get("reportPath") + "/" + TafConstants.get("reportFileName") + j + ".html");
            reporter.config().setDocumentTitle(TafConstants.get("reportTitle"));
            reporter.config().setReportName(TafConstants.get("reportName"));
            reporter.config().thumbnailForBase64(true);
            splitReport.attachReporter(reporter);
            for (int i = k; i < testList.size(); i++) {
                splitReport.getReport().addTest(testList.get(i));
                k++;
                if (k % noOfTestsPerReport == 0) {
                    break;
                }
            }
            splitReport.flush();
        }
    }


    public void createSplitReport() {
        int noOfTestsPerReport = Integer.parseInt(TafConstants.get("noOfTestsPerReport"));
        List<Test> testList = report.getReport().getTestList();
        if (testList.size() == noOfTestsPerReport) {
            report.flush();
            report.attachReporter();
        }
    }
}
