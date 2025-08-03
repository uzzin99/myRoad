package com.jangyujin.myRoad.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    protected Schedule() {}

//    public Schedule(String title, String description, String startDate, String endDate) {
//        this.title = title;
//        this.description = description;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }


}
