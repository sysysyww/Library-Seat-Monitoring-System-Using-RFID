package com.uhf.dataTrans;

public class ChairTag {
    private String EPC;

    public String getEPC() {
        return EPC;
    }

    public int getRSSI() {
        return RSSI;
    }

    public int getCount() {
        return count;
    }

    private int RSSI;
    private int count;

    public ChairTag(String EPC, int RSSI, int count) {
        this.EPC = EPC;
        this.RSSI = RSSI;
        this.count = count;

    }
}
