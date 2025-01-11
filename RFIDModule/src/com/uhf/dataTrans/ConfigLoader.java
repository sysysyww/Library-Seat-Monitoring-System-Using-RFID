package com.uhf.dataTrans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private String epcPrefix;
    private int rssiThreshold;
    private int countThreshold;
    private int floorNumber;
    private int roomNumber;
    private int tableNumber;
    private int chairCount;

    public void loadConfig() throws IOException {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            // Load a properties file from class path
            prop.load(input);

            // Get the property values
            epcPrefix = prop.getProperty("epcPrefix");
            rssiThreshold = Integer.parseInt(prop.getProperty("rssiThreshold"));
            countThreshold = Integer.parseInt(prop.getProperty("countThreshold"));
            floorNumber = Integer.parseInt(prop.getProperty("floorNumber"));
            roomNumber = Integer.parseInt(prop.getProperty("roomNumber"));
            tableNumber = Integer.parseInt(prop.getProperty("tableNumber"));
            chairCount = Integer.parseInt(prop.getProperty("chairCount"));
        }
    }

    // Getters
    public String getEpcPrefix() {
        return epcPrefix;
    }

    public int getRssiThreshold() {
        return rssiThreshold;
    }

    public int getCountThreshold() {
        return countThreshold;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getChairCount() {
        return chairCount;
    }
}
