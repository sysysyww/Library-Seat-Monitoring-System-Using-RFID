package com.uhf.demo;

import java.text.SimpleDateFormat;
import java.util.*;

import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.structures.InventoryArea;
import com.uhf.utils.StringUtils;
import com.uhf.dataTrans.ChairTag;

public class UhfDemo {
    @SuppressWarnings("static-access")
    public static ArrayList<ChairTag> getCurrentTags() {
        int i = Linkage.getInstance().initial("COM4");// 初始化连接设备,参数：端口号
        // function：init， parameter：The port number
        if (i == 0) {
            System.out.println("connect success");
        } else {
            System.out.println("connect failed");
        }
        System.out.println("Begin inventory");
        getInventoryArea();
        setInventoryArea();
        ArrayList<ChairTag> tagList = startInventory();
        stopInventory();
        return tagList;
    }

    public static ArrayList<ChairTag> startInventory() {// 开始盘点 startInventory
        InventoryArea inventory = new InventoryArea();
        inventory.setValue(2, 0, 6);
        Linkage.getInstance().setInventoryArea(inventory);
        InventoryDetailWith.tagCount = 0;// 获取个数  Get the number
        Linkage.getInstance().startInventory(2, 0);
        InventoryDetailWith.startTime = System.currentTimeMillis();// 盘点的开始时间 Start time of Inventory

        int inventoryTimes = 100;
        ArrayList<ChairTag> tagList = new ArrayList<>();
        int maxTimes = 20;
        int i = 0;
        while (InventoryDetailWith.totalCount < inventoryTimes && i < maxTimes) {
            for (Map<String, Object> _map : InventoryDetailWith.list) {
                System.out.println(_map);
            }
            int tag = InventoryDetailWith.list.size();
            System.out.println("标签个数(The number of tag)：" + tag);
            System.out.println("--------------------------------------------");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        for (Map<String, Object> _map : InventoryDetailWith.list) {
            System.out.println(_map);
            tagList.add(new ChairTag((String) _map.get("epc"), Integer.parseInt((String) _map.get("rssi")), Integer.parseInt((String) _map.get("count"))));
        }
        stopInventory();// 进行停止盘点 stopInventory
        return tagList;
    }

    public static void stopInventory() {// 停止盘点 stopInventory
        Linkage.getInstance().stopInventory();
    }

    // 盘点区域获取 getInventoryArea
    public static void getInventoryArea() {
        InventoryArea inventoryArea = new InventoryArea();
        int status = Linkage.getInstance().getInventoryArea(inventoryArea);
        if (status == 0) {
            System.out.println("area:" + inventoryArea.area);
            System.out.println("startAddr:" + inventoryArea.startAddr);
            System.out.println("wordLen:" + inventoryArea.wordLen);
            System.out.println("getInventoryArea success");
            return;
        }
        System.out.println("getInventoryArea failed");
    }

    // 盘点区域设置 setInventoryArea
    public static void setInventoryArea() {
        InventoryArea inventoryArea = new InventoryArea();
        inventoryArea.setValue(2, 0, 6);// 2为epc+user
        int status = Linkage.getInstance().setInventoryArea(inventoryArea);
        if (status == 0) {
            System.out.println("setInventoryArea success");
            return;
        }
        System.out.println("setInventoryArea failed");
    }

}
