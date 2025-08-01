package com.jangyujin.myRoad.domain.schedule.controller;

import com.jangyujin.myRoad.domain.schedule.entity.Schedule;
import com.jangyujin.myRoad.domain.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleApiController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {

        System.out.println("받은 이벤트: " + schedule.getTitle() + ", " + schedule.getDescription() + ", " + schedule.getStartDate() + " ~ " + schedule.getEndDate());

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSchedule);
    }
}
