package com.sq.core;

import java.util.Map;

public class DataManager {

    private static ThreadLocal<Map<String, String>> dataMap = new ThreadLocal<>();

    public static Map<String, String> getDataMap() {
        return dataMap.get();
    }

    public static void setDataMap(Map<String, String> map) {
        dataMap.set(map);
    }
}
