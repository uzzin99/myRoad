package com.jangyujin.myRoad.domain.schedule.service;

import com.jangyujin.myRoad.domain.schedule.entity.Schedule;
import com.jangyujin.myRoad.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    //private final은 한 번만 초기화되고 변경되지 않음을 보장한다.
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // 전체 일정 조회
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // ID로 일정 조회
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }
    
    // 일정 수정
    public Optional<Schedule> updateSchedule(Long id, Schedule scheduleDetails) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);

        if (optionalSchedule.isEmpty()) {
            return Optional.empty();
        }

        Schedule schedule = optionalSchedule.get();
        schedule.setTitle(scheduleDetails.getTitle());
        schedule.setDescription(scheduleDetails.getDescription());
        schedule.setStartDate(scheduleDetails.getStartDate());
        schedule.setEndDate(scheduleDetails.getEndDate());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return Optional.of(updatedSchedule);
    }
}
