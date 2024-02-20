package com.sq.tests;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sq.core.ReportManager;
import com.sq.datahandler.ExcelReader;
import com.sq.helpers.APIAssertHelper;
import com.sq.requests.InvoiceModified;
import com.sq.stepdefs.ApiReusables;
import com.sq.stepdefs.DataPoviders;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SmarteIsApiTestsModified {

    private ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
//    @Test(testName = "Supplier Name Validation", dataProviderClass = DataPoviders.class, dataProvider = "supplierName")
    public void supplierName(String scenario, String value, String expected) {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);
        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel(scenario, ExtentColor.GREEN));
        InvoiceModified modified = mapper.readValue(new File("D:\\Automation\\Intellij\\Framework\\TestAutomationFramework\\TAF\\src\\test\\resources\\requests\\InvoiceRequest.json"), InvoiceModified.class);
        modified.supplierDetails.setName(value);
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", modified, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        List<String> expectedMessageList = Arrays.asList(expected.split(","));
        expectedMessageList = expectedMessageList.stream().map(String::trim).toList();
        System.out.println(expectedMessageList);
        reusables.verifyEquals(response, "message", expectedMessageList);
        assertHelper.assertAll();
    }

    @SneakyThrows
    @Test(testName = "Supplier Email Validation", dataProviderClass = DataPoviders.class, dataProvider = "supplierEmail")
    public void supplierEmail(String scenario, String value, String expected) {
        APIAssertHelper assertHelper = new APIAssertHelper();
        ApiReusables reusables = new ApiReusables(assertHelper);
        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createLabel(scenario, ExtentColor.GREEN));
        InvoiceModified modified = mapper.readValue(new File("D:\\Automation\\Intellij\\Framework\\TestAutomationFramework\\TAF\\src\\test\\resources\\requests\\InvoiceRequest.json"), InvoiceModified.class);
        modified.supplierDetails.setEmail(value);
        Response response = reusables.postRequest("https://sqeims.skill-quotient.com/invoice", modified, Collections.singletonMap("Content-Type", "application/json"));
        reusables.verifyResponseCode(response, 400);
        List<String> expectedMessageList = Arrays.asList(expected.split(","));
        expectedMessageList = expectedMessageList.stream().map(String::trim).toList();
        System.out.println(expectedMessageList);
        reusables.verifyEquals(response, "message", expectedMessageList);
        assertHelper.assertAll();
    }

}

