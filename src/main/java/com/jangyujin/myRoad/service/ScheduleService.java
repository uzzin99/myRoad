package com.jangyujin.myRoad.service;

import com.jangyujin.myRoad.domain.Schedule;
import com.jangyujin.myRoad.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
