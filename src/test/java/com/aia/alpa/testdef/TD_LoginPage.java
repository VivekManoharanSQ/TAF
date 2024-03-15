package com.aia.alpa.testdef;

import com.aia.alpa.pages.LoginPage;
import com.sq.helpers.AssertHelper;
import org.openqa.selenium.WebDriver;

public class TD_LoginPage {

    public LoginPage loginPage;

    private WebDriver driver;

    private AssertHelper assertHelper;


    public TD_LoginPage(WebDriver driver, AssertHelper assertHelper) {
        this.loginPage = new LoginPage(driver);
        this.assertHelper = assertHelper;
    }


    public void loginToApp(String agentCode, String password) {
        loginPage.enterAgentCode(agentCode);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }
}
