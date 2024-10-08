package com.sq.tests;

import com.sq.annotations.Category;
import com.sq.core.DriverManager;
import com.sq.helpers.AssertHelper;
import com.sq.stepdefs.SD_HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class LoginTests {


    @Test(testName = "Success Login", description = "Verify user able to login successfully")
    @Category(categories = {"login","regression"})
    public void loginTest() {
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.loginToApp("Passport No.", "S7612541", "pass");
    }

    @Test(testName = "Verify Id List", description = "Verify the list of values in the drop down")
    @Category(categories = {"regression"})
    public void verifyIdTypes() {
        List<String> expectedValues = Arrays.asList("Please choose ID Type", "Identification Card No.", "Passport No.", "Army No.", "Police No.");
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.selectLanguage();
        sdHomePage.verifyTheDropDownValues(expectedValues);
        assertHelper.assertAll();
    }

    @Test(testName = "Verify Id List Invalid", description = "Verify the list of values in the drop down")
    @Category(categories = {"login","smoke","regression"})
    public void verifyIdTypesInvalid() {
        List<String> expectedValues = Arrays.asList("Please choose ID Typ", "Identification Card No.", "Passport No.", "Army No.", "Police No.");
        WebDriver driver = DriverManager.getDriver();
        AssertHelper assertHelper = new AssertHelper();
        SD_HomePage sdHomePage = new SD_HomePage(driver, assertHelper);
        sdHomePage.selectLanguage();
        sdHomePage.verifyTheDropDownValues(expectedValues);
        assertHelper.assertAll();
    }
}
