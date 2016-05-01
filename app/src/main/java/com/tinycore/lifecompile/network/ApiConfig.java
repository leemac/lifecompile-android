package com.tinycore.lifecompile.network;

public class ApiConfig {
    public static String GetUrl() {
        return EmulatorUrl;
    }

    private final static String EmulatorUrl = "http://localhost:8080";
    private final static String LaptopUrl = "http://192.168.100.148:8080";
    private final static String DesktopUrl = "http://192.168.1.5:8080";
}
