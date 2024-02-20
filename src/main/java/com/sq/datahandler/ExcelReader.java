package com.sq.datahandler;


import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    private XSSFWorkbook xssfWorkbook = null;

    @Getter
    private boolean allSheetsDone = false;
    /**
     * -- GETTER --
     * Return the workbook sheet names
     *
     * @return List<string>
     */
    @Getter
    private List<String> sheetNames = new ArrayList<>();
    private Map<String, Map<String, Map<String, String>>> allSheetsMap = new LinkedHashMap<>();

    /**
     * Constructor method
     * Filename represents the file that would be transformed into a multidimensional map
     * The file name is coming from AUT.properties ->
     * Block
     * Source files data block
     *
     * @param fileName
     */
    public ExcelReader(String fileName) {
        try (FileInputStream fs = new FileInputStream(fileName)) {
            xssfWorkbook = new XSSFWorkbook(fs);
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                sheetNames.add(xssfWorkbook.getSheetName(i));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found : ", e);
        } catch (IOException e) {
            LOGGER.info("IO Exception", e);
        }

    }

    public void setAllSheetsDone(boolean allSheetsDone) {
        this.allSheetsDone = allSheetsDone;
    }

    /**
     * Return all the sheets as Map array
     *
     * @return Map<string, Map < string, Map < string, string>>>
     */
    public Map<String, Map<String, Map<String, String>>> convertAllSheetsToMap() {

        if (allSheetsDone) {
            return allSheetsMap;
        } else {
            for (String sheetName : sheetNames) {
                if (allSheetsMap.containsKey(sheetName)) {
                    continue;
                }
                allSheetsMap.put(sheetName, convertSheetToMap(xssfWorkbook.getSheet(sheetName)));
            }
            allSheetsDone = true;
        }
        return allSheetsMap;
    }

    /**
     * Convert XSSFSheet into a Map
     *
     * @param sheet
     * @return Map<String, Map < String, String>>
     */
    private Map<String, Map<String, String>> convertSheetToMap(XSSFSheet sheet) {

        Map<String, Map<String, String>> sheetMap = new LinkedHashMap();

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getLastCellNum();

        XSSFRow headerRow = sheet.getRow(0);
        List<String> headerRowNames = new ArrayList<>();

        // Data formatter to handle the cell content as text
        DataFormatter formatter = new DataFormatter();
        Cell cellAux;
        String cellString;

        for (int i = 0; i < cols; i++) {
            cellAux = headerRow.getCell(i);
            cellString = formatter.formatCellValue(cellAux);
            headerRowNames.add(cellString);
        }

        for (int i = 1; i < rows; i++) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            XSSFRow dataRow = sheet.getRow(i);
            cellAux = dataRow.getCell(0);
            String key = formatter.formatCellValue(cellAux);
            if (key == null || key.trim().length() == 0) {
                continue;
            }
            for (int j = 0; j < cols; j++) {
                try {
                    XSSFCell cell = dataRow.getCell(j);
                    // adding for cell format to text
                    String auxKey = formatter.formatCellValue(cell);
                    rowMap.put(headerRowNames.get(j), (cell != null) ? auxKey : null);
                } catch (Exception ex) {
                    LOGGER.error("Exception: ", ex);
                    break;
                }
            }
            sheetMap.put(key, rowMap);
        }
        return sheetMap;
    }

    /**
     * Return sheet as a map
     *
     * @param sheetName
     * @return Map of the sheet
     */
    public Map<String, Map<String, String>> getSheetAsMap(String sheetName) {

        try {
            Map<String, Map<String, String>> sheetMap = allSheetsMap.get(sheetName);

            if (sheetMap == null) {
                XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
                if (sheet == null) {
                    throw new DataHandlingException("No such sheet as " + sheetName);
                }
                sheetMap = convertSheetToMap(sheet);
                allSheetsMap.put(sheetName, sheetMap);
            }
            return sheetMap;
        } catch (Exception ex) {
            LOGGER.error("Exception: ", ex);
        }
        return null;
    }

    public List<Map<String, String>> getMultiDataByKey(String sheetName,String dataKey){
        List<Map<String,String>> mapList = new ArrayList<>();
        try {
            List<Map<String, String>> sheetMap = null;

            XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
            if (sheet == null) {
                throw new DataHandlingException("No such sheet as " + sheetName);
            }
            sheetMap = convertSheetToList(sheet,dataKey);
            return sheetMap;
        } catch (Exception ex) {
            LOGGER.error("Exception: ", ex);
        }
        return null;
    }

    private List<Map<String, String>> convertSheetToList(XSSFSheet sheet,String dataKey) {

        List<Map<String, String>> sheetMap = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getLastCellNum();

        XSSFRow headerRow = sheet.getRow(0);
        List<String> headerRowNames = new ArrayList<>();

        // Data formatter to handle the cell content as text
        DataFormatter formatter = new DataFormatter();
        Cell cellAux;
        String cellString;

        for (int i = 0; i < cols; i++) {
            cellAux = headerRow.getCell(i);
            cellString = formatter.formatCellValue(cellAux);
            headerRowNames.add(cellString);
        }

        for (int i = 1; i < rows; i++) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            XSSFRow dataRow = sheet.getRow(i);
            cellAux = dataRow.getCell(0);
            String key = formatter.formatCellValue(cellAux);
            if (key == null || key.trim().length() == 0 || !key.equalsIgnoreCase(dataKey)) {
                continue;
            }
            for (int j = 0; j < cols; j++) {
                try {
                    XSSFCell cell = dataRow.getCell(j);
                    // adding for cell format to text
                    String auxKey = formatter.formatCellValue(cell);
                    rowMap.put(headerRowNames.get(j), (cell != null) ? auxKey : null);
                } catch (Exception ex) {
                    LOGGER.error("Exception: ", ex);
                    break;
                }
            }
            sheetMap.add(rowMap);
        }
        return sheetMap;
    }
}

