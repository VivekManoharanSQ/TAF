package com.aia.alpa.tests;

import com.aia.alpa.testdef.TD_LoginPage;
import com.sq.core.DriverManager;
import com.sq.database.DBSingleton;
import com.sq.helpers.AssertHelper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AIATests {

    @Test
    public void tc_01() {
        WebDriver driver = DriverManager.getDriver();
        TD_LoginPage loginPage = new TD_LoginPage(driver, new AssertHelper());
        loginPage.loginToApp("K7373", "pass123");
        try {
            List<HashMap> test = DBSingleton.getValData("select * from Employee");
            System.out.println(test.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
