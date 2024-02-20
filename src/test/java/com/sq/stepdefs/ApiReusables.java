package com.sq.stepdefs;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sq.core.ReportManager;
import com.sq.helpers.APIAssertHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.util.Map;

public class ApiReusables {
    private static final ObjectMapper mapper = new ObjectMapper();
    private APIAssertHelper apiAssertHelper;

    public ApiReusables(APIAssertHelper apiAssertHelper) {
        this.apiAssertHelper = apiAssertHelper;
    }

    @SneakyThrows
    public Response postRequest(String url, Object requestBody, Map<String, String> headers) {
        RequestSpecification spec = RestAssured.given();
        if (null != headers || headers.isEmpty()) {
            spec.headers(headers);
        }
        ReportManager.getExtentTest().log(Status.INFO, MarkupHelper.createJsonCodeBlock(requestBody));
        Response response = spec.body(requestBody).post(url);
        logResponse(response);
        return response;
    }

    public void logResponse(Response response) {
        ReportManager.getExtentTest()
                .log(Status.INFO, MarkupHelper.createCodeBlock(response.asPrettyString(), CodeLanguage.JSON));
    }

    public void verifyResponseCode(Response response, int expectedStatusCode) {
        int statusCode = response.getStatusCode();
        apiAssertHelper.assertEquals(statusCode, expectedStatusCode, "Verify Status Code");
    }

    public void verifyEquals(Response response, String path, Object expectedValue) {
        Object actualObj = response.jsonPath().get(path);
        System.out.println(actualObj);
//        response.then().assertThat().body("$", Matchers.hasKey(path));
        apiAssertHelper.assertEquals(actualObj, expectedValue, "Verify Value " + path);
    }
}
