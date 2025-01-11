package org.group7.myapplication.service;

import org.group7.myapplication.entity.LibraryInfo;
import org.group7.myapplication.model.AggregatedLibraryInfo;
import org.group7.myapplication.repository.LibraryInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LibraryInfoService {

    @Autowired
    private LibraryInfoRepository repository;

    @Value("${aggregation.time-interval}")
    private int timeInterval;

    private LocalDateTime adjustToNearestInterval(LocalDateTime time) {
        ZoneId chinaZoneId = ZoneId.of("Asia/Shanghai");

        long minutesFromEpoch = time.atZone(chinaZoneId).toEpochSecond() / 60;
        long nearestInterval = (minutesFromEpoch + timeInterval / 2) / timeInterval * timeInterval;
        return Instant.ofEpochSecond(nearestInterval * 60).atZone(chinaZoneId).toLocalDateTime();
    }

    public LibraryInfo saveLibraryInfo(LibraryInfo info) {
        return repository.save(info);
    }

    public List<LibraryInfo> getFilteredData(Integer floorNumber, Integer roomNumber, Integer tableNumber, LocalDateTime startTime, LocalDateTime endTime) {
        Specification<LibraryInfo> spec = Specification.where(null);

        if (floorNumber != null) {
            spec = spec.and(LibraryInfoSpecifications.hasFloorNumber(floorNumber));
        }
        if (roomNumber != null) {
            spec = spec.and(LibraryInfoSpecifications.hasRoomNumber(roomNumber));
        }
        if (tableNumber != null) {
            spec = spec.and(LibraryInfoSpecifications.hasTableNumber(tableNumber));
        }
        if (startTime != null && endTime != null) {
            spec = spec.and(LibraryInfoSpecifications.isWithinTimeRange(startTime, endTime));
        }

        return repository.findAll(spec);
    }

    public List<AggregatedLibraryInfo> getVisData(Integer floorNumber, Integer roomNumber, Integer tableNumber, LocalDateTime startTime, LocalDateTime endTime) {
        List<LibraryInfo> filteredInfo = getFilteredData(floorNumber, roomNumber, tableNumber, startTime, endTime);

        Map<LocalDateTime, AggregatedLibraryInfo> aggregationMap = new HashMap<>();

        for (LibraryInfo info : filteredInfo) {
            LocalDateTime adjustedTime = adjustToNearestInterval(info.getTime());
            AggregatedLibraryInfo aggregatedData = aggregationMap.get(adjustedTime);

            if (aggregatedData == null) {
                aggregatedData = new AggregatedLibraryInfo();
                aggregatedData.setTime(adjustedTime);
                aggregatedData.setTotalSeats(info.getTotalChairCount());
                aggregatedData.setEmptySeats(info.getEmptyChairCount());
                aggregationMap.put(adjustedTime, aggregatedData);
            } else {
                aggregatedData.setTotalSeats(aggregatedData.getTotalSeats() + info.getTotalChairCount());
                aggregatedData.setEmptySeats(aggregatedData.getEmptySeats() + info.getEmptyChairCount());
            }
        }

        return new ArrayList<>(aggregationMap.values());
    }

    // 其他业务逻辑...
}

