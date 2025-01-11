package org.group7.myapplication.controller;

import org.group7.myapplication.entity.LibraryInfo;
import org.group7.myapplication.model.AggregatedLibraryInfo;
import org.group7.myapplication.model.Node;
import org.group7.myapplication.service.LibraryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/libraryData")
public class LibraryInfoController {
    @Value("${aggregation.time-interval}")
    private int timeIntervalMinutes;
    @Autowired
    private LibraryInfoService libraryInfoService;

    @CrossOrigin
    @GetMapping("/dataVis")
    public ResponseEntity<List<AggregatedLibraryInfo>> getFilteredVisData(@RequestParam(required = false) Integer floorNumber, @RequestParam(required = false) Integer roomNumber, @RequestParam(required = false) Integer tableNumber, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<AggregatedLibraryInfo> data = libraryInfoService.getVisData(floorNumber, roomNumber, tableNumber, startTime, endTime);
        return ResponseEntity.ok(data.stream()
                .sorted(Comparator.comparing(AggregatedLibraryInfo::getTime))
                .collect(Collectors.toList()));
    }

    @CrossOrigin
    @GetMapping("/dataCurrent")
    public ResponseEntity<List<AggregatedLibraryInfo>> getFilteredVisDataRevers(@RequestParam(required = false) Integer floorNumber, @RequestParam(required = false) Integer roomNumber, @RequestParam(required = false) Integer tableNumber, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<AggregatedLibraryInfo> data = libraryInfoService.getVisData(floorNumber, roomNumber, tableNumber, startTime, endTime);
        List<AggregatedLibraryInfo> originalData = data.stream()
                .sorted(Comparator.comparing(AggregatedLibraryInfo::getTime).reversed())
                .collect(Collectors.toList());
        List<AggregatedLibraryInfo> singleItemList = new ArrayList<>();
        if (!originalData.isEmpty()) {
            singleItemList.add(originalData.get(0));
        }
        return ResponseEntity.ok(singleItemList);
    }

    @CrossOrigin
    @GetMapping("/TreeJson")
    public ResponseEntity<List<Node>> returnJson() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(30);
        List<LibraryInfo> filteredInfo = libraryInfoService.getFilteredData(null, null, null, startTime, endTime);
        return ResponseEntity.ok(Node.aggregateData(filteredInfo));

    }
}
