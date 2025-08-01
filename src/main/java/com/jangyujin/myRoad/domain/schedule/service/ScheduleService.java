package com.jangyujin.myRoad.domain.schedule.service;

import com.jangyujin.myRoad.domain.schedule.entity.Schedule;
import com.jangyujin.myRoad.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    //private final은 한 번만 초기화되고 변경되지 않음을 보장한다.
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
