package com.sq.stepdefs;

import com.sq.datahandler.ExcelReader;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class DataPoviders {
    private ExcelReader reader = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/datafiles/TestData.xlsx");

    @DataProvider
    public Object[][] supplierName() {
        List<Map<String, String>> data = reader.getMultiDataByKey("Sheet1", "supplierName");
        return dataMapper(data);
    }

    @DataProvider
    public Object[][] supplierEmail() {
        List<Map<String, String>> data = reader.getMultiDataByKey("Sheet1", "supplierEmail");
        return dataMapper(data);
    }

    private Object[][] dataMapper(List<Map<String,String>> dataList){
        Object[][] dataObj = new Object[dataList.size()][dataList.get(0).size() - 1];
        for (int i = 0; i < dataList.size(); i++) {
            dataObj[i][0] = dataList.get(i).get("Scenario");
            dataObj[i][1] = dataList.get(i).get("Value");
            dataObj[i][2] = dataList.get(i).get("message");
        }
        return dataObj;
    }
}
