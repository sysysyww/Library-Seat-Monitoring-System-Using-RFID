package org.group7.myapplication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.group7.myapplication.entity.LibraryInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
    private String name;
    private int value;
    private List<Node> children;

    public Node(String name, int value, List<Node> children) {
        this.name = name;
        this.value = value;
        this.children = children;
    }

    public Node() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public static List<Node> aggregateData(List<LibraryInfo> libraryInfos) {
        Map<Integer, Node> floorMap = new HashMap<>();

        for (LibraryInfo info : libraryInfos) {
            // 处理楼层
            Node floorNode = floorMap.computeIfAbsent(info.getFloorNumber(), k -> new Node("floor" + k, 0, new ArrayList<>()));

            // 处理房间
            Map<Integer, Node> roomMap = new HashMap<>(); // 房间映射
            for (Node room : floorNode.getChildren()) {
                roomMap.put(Integer.parseInt(room.getName().substring(4)), room); // 假设房间名为 "room" + 数字
            }
            Node roomNode = roomMap.computeIfAbsent(info.getRoomNumber(), k -> new Node("room" + k, 0, new ArrayList<>()));
            if (!floorNode.getChildren().contains(roomNode)) {
                floorNode.getChildren().add(roomNode);
            }

            // 处理桌子
            Map<Integer, Node> tableMap = new HashMap<>(); // 桌子映射
            for (Node table : roomNode.getChildren()) {
                tableMap.put(Integer.parseInt(table.getName().substring(5)), table); // 假设桌子名为 "table" + 数字
            }
            Node tableNode = tableMap.computeIfAbsent(info.getTableNumber(), k -> new Node("table" + k, info.getEmptyChairCount(), null));
            if (!roomNode.getChildren().contains(tableNode)) {
                roomNode.getChildren().add(tableNode);
            }

            // 更新计数
            tableNode.setValue(tableNode.getValue() + info.getEmptyChairCount());
            roomNode.setValue(roomNode.getValue() + info.getEmptyChairCount());
            floorNode.setValue(floorNode.getValue() + info.getEmptyChairCount());
        }

        return new ArrayList<>(floorMap.values());
    }


}
