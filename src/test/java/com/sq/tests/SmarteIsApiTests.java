package com.sq.tests;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.sq.core.ReportManager;
import com.sq.datahandler.ExcelReader;
import com.sq.helpers.APIAssertHelper;
import com.sq.requests.InvoiceCreate;
import com.sq.stepdefs.ApiReusables;
import io.restassured.response.Response;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SmarteIsApiTests implements ITest {

//    private final

//  Alphanumeric
//  special char
//  empty
//  invalid other than acceptable values
//  length(More than, less than)
//MSIC code.

//    !@#$%^&*

    @Test(testName = "MSIC Code Validation Invoice", enabled = true, dataProvider = "msicDataProvider")
    public void msicInvoice(String scenario, String value, String expected) {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);

        InvoiceCreate request = new InvoiceCreate();
        request.setPartyReqInvoiceId("SPI-2023-12-14150");

        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel(scenario, ExtentColor.GREEN));
        request.setPartyMsicCode(value);
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        reusables.verifyEquals(response, "error", "Bad Request");
        reusables.verifyEquals(response, "message[0]", expected);
        assertHelper.assertAll();
    }

    @Test(testName = "MSIC Code Validation Credit Note", enabled = true, dataProvider = "msicDataProvider")
    public void msicCreditNote(String scenario, String value, String expected) {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);

        InvoiceCreate request = new InvoiceCreate();
        request.setPartyReqInvoiceId("SPI-2023-12-14150");
        request.setInvoiceType("02");
        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel(scenario, ExtentColor.GREEN));
        request.setPartyMsicCode(value);
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        reusables.verifyEquals(response, "error", "Bad Request");
        reusables.verifyEquals(response, "message[0]", expected);
        assertHelper.assertAll();
    }

    @Test(testName = "Classification code", enabled = false)
    public void classificationCodeTest() {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);
        InvoiceCreate request = new InvoiceCreate();
        request.setPartyReqInvoiceId("SPI-2023-12-14150");

        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel("Alphanumeric Test", ExtentColor.GREEN));
        request.setClassification("011abc");
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        reusables.verifyEquals(response, "error", "Bad Request");

        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel("Length above 3", ExtentColor.GREEN));
        request.setClassification("011111");
        response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        reusables.verifyEquals(response, "error", "Bad Request");
        reusables.verifyEquals(response, "message[0]", "The Classification Code does not follow the standard code, length must be 3 characters long");

        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel("Length below 3", ExtentColor.GREEN));
        request.setClassification("01");
        response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        reusables.verifyEquals(response, "error", "Bad Request");
        reusables.verifyEquals(response, "message[0]", "The Classification Code does not follow the standard code, length must be 3 characters long");

        String[] specialChar = {"@", "!", "$"};
        for (String s : specialChar) {
            ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel("Special Character Test : " + s, ExtentColor.GREEN));
            request.setClassification(s);
            response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
            reusables.verifyResponseCode(response, 400);
            reusables.verifyEquals(response, "error", "Bad Request");
            reusables.verifyEquals(response, "message[0]", "The Classification Code does not follow the standard code, length must be 3 characters long");
        }
        assertHelper.assertAll();
    }

    //    @Test(testName = "Success Test")
    public void successTest() {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);
        InvoiceCreate request = new InvoiceCreate();
        request.setPartyReqInvoiceId("SPI-2023-12-14155");
        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel("Success", ExtentColor.GREEN));
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", request, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyEquals(response, "statusCode", 200);
        reusables.verifyEquals(response, "message", "");
        reusables.verifyEquals(response, "data.irbmStatus", "Validated");
        assertHelper.assertAll();
    }

    @Override
    public String getTestName() {
        return "Sample Test";
    }

    @DataProvider
    public Object[][] msicDataProvider() {
        ExcelReader reader = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/datafiles/TestData.xlsx");
        List<Map<String, String>> data = reader.getMultiDataByKey("Sheet1", "msicInvoice");
        Object[][] dataObj = new Object[data.size()][data.get(0).size()-1];

        for(int i=0;i<data.size();i++){
            dataObj[i][0] = data.get(i).get("Scenario");
            dataObj[i][1] = data.get(i).get("Value");
            dataObj[i][2] = data.get(i).get("message");
        }
//        for (Object[] d : dataObj) {
//
//        }
//        dataObj[0][0] = "AlphaNumeric";
//        dataObj[0][1] = "123ad";
//        dataObj[0][2] = "Party  MSICCode: Only Numbers are allowed";
//
//        dataObj[1][0] = "Length below 3";
//        dataObj[1][1] = "011";
//        dataObj[1][2] = "The MSIC Code does not follow the standard code, length must be 5 characters long";
//
//        dataObj[2][0] = "Length above 5";
//        dataObj[2][1] = "011111";
//        dataObj[2][2] = "The MSIC Code does not follow the standard code, length must be 5 characters long";
        System.out.println(dataObj);
        return dataObj;
    }
}
