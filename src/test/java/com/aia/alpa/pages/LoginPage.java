package com.aia.alpa.pages;

import com.sq.core.MobileUI;
import com.sq.core.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends MobileUI {

    private final By txtYourAgentCode = By.xpath("//*[@text='Your Agent Code']");

    private final By txtYourPassword = By.xpath("//*[@text='Your Password']");

    private final By btnLogin = By.xpath("//*[@text='LOGIN']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterAgentCode(String agentCode){
        sendKeys(txtYourAgentCode,agentCode);
    }

    public void enterPassword(String password){
        sendKeys(txtYourPassword,password);
    }

    public void clickLogin(){
        click(btnLogin);
    }
}
