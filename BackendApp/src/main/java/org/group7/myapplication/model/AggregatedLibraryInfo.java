package org.group7.myapplication.model;

import java.time.LocalDateTime;

public class AggregatedLibraryInfo {
    private LocalDateTime time;
    private Integer totalSeats;
    private Integer emptySeats;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getEmptySeats() {
        return emptySeats;
    }

    public void setEmptySeats(Integer emptySeats) {
        this.emptySeats = emptySeats;
    }

    public AggregatedLibraryInfo(LocalDateTime time, Integer totalSeats, Integer emptySeats) {
        this.time = time;
        this.totalSeats = totalSeats;
        this.emptySeats = emptySeats;
    }

    public AggregatedLibraryInfo() {

    }
}
