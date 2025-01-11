package org.group7.myapplication.service;

import org.group7.myapplication.entity.LibraryInfo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class LibraryInfoSpecifications {
    public static Specification<LibraryInfo> hasFloorNumber(Integer floorNumber) {
        return (root, query, criteriaBuilder) -> {
            if (floorNumber == null)
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filter
            return criteriaBuilder.equal(root.get("floorNumber"), floorNumber);
        };
    }

    public static Specification<LibraryInfo> hasRoomNumber(Integer roomNumber) {
        return (root, query, criteriaBuilder) -> {
            if (roomNumber == null)
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filter
            return criteriaBuilder.equal(root.get("roomNumber"), roomNumber);
        };
    }

    public static Specification<LibraryInfo> hasTableNumber(Integer tableNumber) {
        return (root, query, criteriaBuilder) -> {
            if (tableNumber == null)
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filter
            return criteriaBuilder.equal(root.get("tableNumber"), tableNumber);
        };
    }

    public static Specification<LibraryInfo> isWithinTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, criteriaBuilder) -> {
            if (startTime == null || endTime == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // no time filter
            }
            return criteriaBuilder.between(root.get("time"), startTime, endTime);
        };
    }

}
