package com.sq.stepdefs;

import com.aventstack.extentreports.Status;
import com.sq.core.ReportManager;
import com.sq.helpers.AssertHelper;
import com.sq.pages.HomePage;
import com.sq.utils.Screenshot;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SD_HomePage extends HomePage {

    private WebDriver driver;

    private AssertHelper assertHelper;
    private HomePage homePage;

    public SD_HomePage(WebDriver driver, AssertHelper assertHelper) {
        super(driver);
        this.driver = driver;
        this.homePage = new HomePage(driver);
        this.assertHelper = assertHelper;
    }

    @SneakyThrows
    public void loginToApp(String idType, String idValue, String password) {
        homePage.selectLanguage();
        ReportManager.getExtentTest().log(Status.INFO, "App Launched Auccessfully", Screenshot.attachScreenShot());
        Thread.sleep(2000);
        homePage.selectIdType(idType);
        homePage.enterIdValue(idValue);
        homePage.clickSubmit();
    }


    public void verifyTheDropDownValues(List<String> expectedList) {
        ReportManager.getExtentTest().log(Status.INFO, "App Launched Auccessfully", Screenshot.attachScreenShot());
        assertHelper.assertEquals(homePage.getIdTypeList(), expectedList, "Verify the list of options");
    }
}
