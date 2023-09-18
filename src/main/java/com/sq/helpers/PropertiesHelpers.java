package com.sq.helpers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelpers {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHelpers.class);
    private static final String TEST_RESOURCES = System.getProperty("user.dir") + "/src/test/resources/properties/";
    private static Map<String, Properties> propertiesMap = new HashMap<>();


    private static void loadAllPropertiesFiles() {
        Collection<File> files = FileUtils.listFiles(new File(TEST_RESOURCES),
                new String[]{"properties"}, true);
        for (File file : files) {
            loadProperties(file);
        }
    }

    private static void loadProperties(File propertyFile) {
        try {
            FileInputStream fis = new FileInputStream(propertyFile);
            Properties tempProps = new Properties();
            tempProps.load(fis);
            propertiesMap.put(FilenameUtils.removeExtension(propertyFile.getName()), tempProps);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("Warning !! Can't Load File.");
        }
    }

    public static Properties getPropertiesOfFile(String fileNameWoExtension) {
        if (propertiesMap.containsKey(fileNameWoExtension))
            return propertiesMap.get(fileNameWoExtension);
        else {
            loadProperties(new File(TEST_RESOURCES + fileNameWoExtension + ".properties"));
            return propertiesMap.get(fileNameWoExtension);
        }
    }
}
