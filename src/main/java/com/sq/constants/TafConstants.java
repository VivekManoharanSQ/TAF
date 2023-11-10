package com.sq.constants;

import com.google.common.collect.ImmutableMap;
import com.sq.helpers.PropertiesHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


public abstract class TafConstants {
    private static final Logger LOGGER = LoggerFactory.getLogger(TafConstants.class);

    private static final ThreadLocal<Map<String,String>> propertiesThreadLocal = new ThreadLocal<>();
    private static final ImmutableMap<String, String> DEFAULTS = ImmutableMap.of("runRemote", "false");

    public static String get(String property) {
        Map<String,String> tempProperties = propertiesThreadLocal.get();
        if (tempProperties.containsKey(property)){
            System.out.println(tempProperties.get(property));
            return tempProperties.get(property);
        }
        else if (DEFAULTS.containsKey(property))
            return DEFAULTS.get(property);
        else {
            LOGGER.error("Property " + property + " is not defined in the config.properties file hence returning empty string");
            return "";
        }
    }

    public static void setExecutionParams(Map<String, String> xmlParams) {
        Properties tempProperties = PropertiesHelpers.getPropertiesOfFile("config");
        Map<String,String> tempMap = new HashMap<>();
        tempProperties.forEach((key, value) -> {
            if (System.getProperties().containsKey(key)) {
                tempMap.put(key.toString(), System.getProperty(key.toString()));
            } else if (xmlParams.containsKey(key.toString())) {
                tempMap.put(key.toString(), xmlParams.get(key.toString()));
            }else{
                tempMap.put(key.toString(), value.toString());
            }
        });
        propertiesThreadLocal.set(tempMap);

    }

}


