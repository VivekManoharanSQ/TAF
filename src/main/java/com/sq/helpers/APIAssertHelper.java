package com.sq.helpers;

import com.aventstack.extentreports.Status;
import com.sq.core.ReportManager;
import com.sq.utils.Screenshot;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class APIAssertHelper extends SoftAssert {

    @Override
    public void onAssertFailure(IAssert<?> var1, AssertionError var2) {
        ReportManager
                .getExtentTest()
                .log(Status.FAIL, var1.getMessage() +
                        "<br> Expected : " + var1.getExpected() +
                        "<br> Actual : " + var1.getActual());
    }

    @Override
    public void onAssertSuccess(IAssert<?> var1) {
        ReportManager
                .getExtentTest()
                .log(Status.PASS, var1.getMessage() +
                        "<br> Expected : " + var1.getExpected() +
                        "<br> Actual : " + var1.getActual());
    }
}
