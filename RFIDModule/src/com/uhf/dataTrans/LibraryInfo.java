package com.uhf.dataTrans;

import java.time.LocalDateTime;
import java.io.IOException;

public class LibraryInfo {
    private int floorNumber;
    private int roomNumber;
    private int tableNumber;
    private int emptyChairCount;
    private int totalChairCount;
    private LocalDateTime time;

    public LibraryInfo(int emptyChairCount) {
        ConfigLoader config = new ConfigLoader();
        try {
            config.loadConfig();

            // 使用配置值
            this.floorNumber = config.getFloorNumber();
            this.roomNumber = config.getRoomNumber();
            this.tableNumber = config.getTableNumber();
            this.totalChairCount = config.getChairCount();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.emptyChairCount = emptyChairCount;
        LocalDateTime now = LocalDateTime.now();
        time = now;
    }
}
