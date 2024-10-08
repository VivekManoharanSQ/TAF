package com.sq.pages;

import com.sq.core.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends WebUI {

    private final By ddIdType = By.tagName("select");

    private final By txtIdNo = By.xpath("//input[normalize-space(@placeholder)='New Identification No.']");

    private final By btnSubmit = By.tagName("button");

    private final By btnEng = By.xpath("//p[text()='ENG']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void selectIdType(String idType) {
        selectOptionByVisibleText(ddIdType, idType);
    }

    public void enterIdValue(String idValue) {
        sendKeys(txtIdNo, idValue, "Identification No");
    }

    public void clickSubmit() {
        click(btnSubmit);
    }

    public List<String> getIdTypeList() {
        click(ddIdType);
        return getSelect(ddIdType).getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectLanguage(){
        click(btnEng);
    }
}
