package com.sq.constants;

import com.google.common.collect.ImmutableMap;
import com.sq.helpers.PropertiesHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;


public abstract class TafConstants {
    private static final Logger LOGGER = LoggerFactory.getLogger(TafConstants.class);

    private static final Properties CONFIG_PROPERTIES = PropertiesHelpers.getPropertiesOfFile("config");

    private static final ImmutableMap<String, String> DEFAULTS = ImmutableMap.of("runRemote", "false");

    public static String get(String property) {
        if (CONFIG_PROPERTIES.containsKey(property))
            return CONFIG_PROPERTIES.getProperty(property);
        else if (DEFAULTS.containsKey(property))
            return DEFAULTS.get(property);
        else {
            LOGGER.error("Property " + property + " is not defined in the config.properties file hence returning empty string");
            return "";
        }
    }

    public static void setExecutionParams(Map<String, String> xmlParams) {
        CONFIG_PROPERTIES.forEach((key, value) -> {
            if (System.getProperties().containsKey(key)) {
                CONFIG_PROPERTIES.put(key, System.getProperty(key.toString()));
            } else if (xmlParams.containsKey(key.toString())) {
                CONFIG_PROPERTIES.put(key, xmlParams.get(key.toString()));
            }
        });
    }
}


