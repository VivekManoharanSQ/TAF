package com.sq.tests;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BS_MainTestMobileDownload {


    public static void main(String[] args) throws InterruptedException, MalformedURLException {

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", "chrome");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("osVersion", "10.0");
        browserstackOptions.put("deviceName", "Samsung Galaxy S20");
        browserstackOptions.put("local", "false");
        capabilities.setCapability("bstack:options", browserstackOptions);
        System.out.println(capabilities);
        WebDriver driver = new RemoteWebDriver(new URL("https://vivekm_a7Q43Y:LHHojWzbMraFv7zVGWX7@hub-cloud.browserstack.com/wd/hub"),capabilities);
        driver.get("https://file-examples.com/wp-content/storage/2017/10/file-sample_150kB.pdf");
        Thread.sleep(20000);
        driver.quit();
    }
}
