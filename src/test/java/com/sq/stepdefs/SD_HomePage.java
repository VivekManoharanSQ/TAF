package com.sq.stepdefs;

import com.aventstack.extentreports.Status;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import com.sq.pages.HomePage;
import org.openqa.selenium.WebDriver;

public class SD_HomePage {

    private WebDriver driver;

    private AssertHelper assertHelper;
    private HomePage homePage;

    public SD_HomePage(WebDriver driver, AssertHelper assertHelper) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
        this.assertHelper = assertHelper;
    }

    public void clickOnDocumentation() {

        ReportManager.getExtentTest().log(Status.INFO, "test");
        assertHelper.assertEquals(1, 12, "Verify the numbers");
        homePage.clickDocumentationLink();
    }
}
