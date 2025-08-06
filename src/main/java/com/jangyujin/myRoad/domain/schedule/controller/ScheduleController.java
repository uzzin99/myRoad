package com.jangyujin.myRoad.domain.schedule.controller;

import com.jangyujin.myRoad.domain.schedule.entity.Schedule;
import com.jangyujin.myRoad.domain.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping({"/", "/schedule"})
    public String mainPage(Model model) {
        return "pages/main";
    }

    @GetMapping("/schedule/list")
    public String ScheduleList(Model model) {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "pages/schedule-list";
    }
}
