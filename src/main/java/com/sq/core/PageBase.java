package com.sq.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageBase {

    private WebDriver driver;

    private WebElement element;


    public PageBase(WebDriver driver){

    }

    public void findElement(By byObject){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void click(){

    }

    public void sendKeys(){

    }


}
