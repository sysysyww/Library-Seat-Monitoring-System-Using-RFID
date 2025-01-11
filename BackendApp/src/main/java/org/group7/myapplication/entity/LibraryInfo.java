package org.group7.myapplication.entity;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "library_info")
@Getter
@Setter
@NoArgsConstructor  // 自动生成无参数构造器
public class LibraryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer floorNumber;
    private Integer roomNumber;
    private Integer tableNumber;
    private Integer emptyChairCount;
    private Integer totalChairCount;
    @Column(name = "time")
    private LocalDateTime time;


    @Override
    public String toString() {
        return "LibraryInfo{" +
                "floorNumber=" + floorNumber +
                ", roomNumber=" + roomNumber +
                ", tableNumber=" + tableNumber +
                ", emptyChairCount=" + emptyChairCount +
                ", totalChairCount=" + totalChairCount +
                ", currentTime='" + time + '\'' +
                '}';
    }
}