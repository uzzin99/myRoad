package com.jangyujin.myRoad.domain.schedule.repository;

import com.jangyujin.myRoad.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
